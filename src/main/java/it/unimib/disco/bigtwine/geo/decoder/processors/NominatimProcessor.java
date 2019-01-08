package it.unimib.disco.bigtwine.geo.decoder.processors;

import it.unimib.disco.bigtwine.geo.decoder.Decoder;
import it.unimib.disco.bigtwine.geo.decoder.executors.GeoSyncExecutor;

public final class NominatimProcessor extends GeoSyncProcessor {

    public NominatimProcessor(GeoSyncExecutor executor) {
        super(executor);
    }

    @Override
    public Decoder getDecoder() {
        return Decoder.nominatim;
    }

    @Override
    public String getProcessorId() {
        return "nominatim-processor";
    }
}
