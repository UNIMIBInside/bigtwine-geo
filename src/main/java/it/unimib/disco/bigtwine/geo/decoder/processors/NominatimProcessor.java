package it.unimib.disco.bigtwine.geo.decoder.processors;

import it.unimib.disco.bigtwine.commons.executors.Executor;
import it.unimib.disco.bigtwine.commons.models.Address;
import it.unimib.disco.bigtwine.commons.processors.ProcessorListener;
import it.unimib.disco.bigtwine.geo.decoder.Decoder;

public final class NominatimProcessor implements Processor {
    @Override
    public Decoder getDecoder() {
        return null;
    }

    @Override
    public String getProcessorId() {
        return null;
    }

    @Override
    public void setExecutor(Executor executor) {

    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void setListener(ProcessorListener<Address> listener) {

    }

    @Override
    public boolean configureProcessor() {
        return false;
    }

    @Override
    public boolean process(String tag, String item) {
        return false;
    }

    @Override
    public boolean process(String tag, String[] items) {
        return false;
    }
}
