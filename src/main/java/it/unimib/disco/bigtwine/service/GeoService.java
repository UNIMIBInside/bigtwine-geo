package it.unimib.disco.bigtwine.service;

import it.unimib.disco.bigtwine.commons.messaging.GeoDecoderRequestMessage;
import it.unimib.disco.bigtwine.commons.messaging.GeoDecoderResponseMessage;
import it.unimib.disco.bigtwine.commons.models.Address;
import it.unimib.disco.bigtwine.commons.processors.GenericProcessor;
import it.unimib.disco.bigtwine.commons.processors.ProcessorListener;
import it.unimib.disco.bigtwine.geo.decoder.Decoder;
import it.unimib.disco.bigtwine.geo.decoder.processors.Processor;
import it.unimib.disco.bigtwine.geo.decoder.processors.ProcessorFactory;
import it.unimib.disco.bigtwine.messaging.GeoDecoderRequestsConsumerChannel;
import it.unimib.disco.bigtwine.messaging.GeoDecoderResponsesProducerChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeoService implements ProcessorListener<Address> {

    private final Logger log = LoggerFactory.getLogger(GeoService.class);
    private MessageChannel channel;
    private ProcessorFactory processorFactory;
    private Map<Decoder, Processor> processors = new HashMap<>();

    public GeoService(
        GeoDecoderResponsesProducerChannel channel,
        ProcessorFactory processorFactory) {
        this.channel = channel.geoDecoderResponsesChannel();
        this.processorFactory = processorFactory;
    }

    private Decoder getDecoder(String decoderId) {
        if (decoderId != null) {
            decoderId = decoderId.trim();
            if (decoderId.equals("default")) {
                return Decoder.getDefault();
            }else {
                try {
                    return Decoder.valueOf(decoderId);
                }catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }else {
            return Decoder.getDefault();
        }
    }

    private Processor getProcessor(Decoder decoder) {
        Processor processor;
        if (this.processors.containsKey(decoder)) {
            processor = this.processors.get(decoder);
        }else {
            try {
                processor = this.processorFactory.getProcessor(decoder);
            } catch (Exception e) {
                System.err.println("Cannot create processor");
                log.error("Cannot create processor");
                return null;
            }
            processor.setListener(this);
            boolean processorReady = processor.configureProcessor();
            if (processorReady) {
                this.processors.put(decoder, processor);
            }else {
                System.err.println("Processor not ready: " + processor.getDecoder().toString());
                log.error("Processor not ready: " + processor.getDecoder().toString());
                return null;
            }
        }

        log.info("Processor ready: " + processor.getClass().toString());

        return processor;
    }

    private void processDecodeRequest(GeoDecoderRequestMessage request) {
        Decoder decoder = this.getDecoder(request.getDecoder());

        if (decoder == null) {
            return;
        }

        Processor processor = this.getProcessor(decoder);

        if (processor == null) {
            return;
        }

        processor.process(request.getRequestId(), request.getAddresses());
    }


    private void sendResponse(Processor processor, String tag, Address[] addresses) {
        // for (LinkedTweet tweet : tweets) {
        //      System.out.println("Linked tweet: " + tweet.getId());
        // }
        GeoDecoderResponseMessage response = new GeoDecoderResponseMessage();
        response.setDecoder(processor.getDecoder().toString());
        response.setAddresses(addresses);
        response.setRequestId(tag);
        this.channel.send(MessageBuilder.withPayload(response).build());
        log.info("Request Processed: {}.", tag);
    }


    @StreamListener(GeoDecoderRequestsConsumerChannel.CHANNEL)
    public void onNewDecodeRequest(GeoDecoderRequestMessage request) {
        log.info("Request Received: {}.", request.getRequestId());
        this.processDecodeRequest(request);
    }

    @Override
    public void onProcessed(GenericProcessor processor, String tag, Address[] processedItems) {
        if (!(processor instanceof Processor)) {
            throw new AssertionError("Invalid processor type");
        }

        this.sendResponse((Processor)processor, tag, processedItems);
    }
}
