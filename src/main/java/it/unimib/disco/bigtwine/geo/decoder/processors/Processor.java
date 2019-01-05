package it.unimib.disco.bigtwine.geo.decoder.processors;

import it.unimib.disco.bigtwine.commons.models.Address;
import it.unimib.disco.bigtwine.commons.processors.GenericProcessor;
import it.unimib.disco.bigtwine.geo.decoder.Decoder;

public interface Processor extends GenericProcessor<String, Address> {
    Decoder getDecoder();
}
