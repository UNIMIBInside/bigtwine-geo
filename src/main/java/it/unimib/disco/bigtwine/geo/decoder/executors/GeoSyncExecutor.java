package it.unimib.disco.bigtwine.geo.decoder.executors;

import it.unimib.disco.bigtwine.commons.executors.SyncExecutor;
import it.unimib.disco.bigtwine.commons.models.Address;

public interface GeoSyncExecutor extends SyncExecutor {
    Address search(String location);
    Address[] search(String[] locations);
}
