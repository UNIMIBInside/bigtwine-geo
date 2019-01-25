package it.unimib.disco.bigtwine.services.geo.decoder.processors;

import it.unimib.disco.bigtwine.commons.models.DecodedLocation;
import it.unimib.disco.bigtwine.commons.models.Location;
import it.unimib.disco.bigtwine.commons.processors.GenericProcessor;
import it.unimib.disco.bigtwine.services.geo.decoder.Decoder;

public interface Processor extends GenericProcessor<Location, DecodedLocation> {
    Decoder getDecoder();
}
