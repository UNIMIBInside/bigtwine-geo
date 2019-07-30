package it.unimib.disco.bigtwine.services.geo.decoder.executors;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.model.Address;
import it.unimib.disco.bigtwine.commons.models.DecodedLocation;
import it.unimib.disco.bigtwine.commons.models.Coordinate;
import it.unimib.disco.bigtwine.commons.models.Location;
import it.unimib.disco.bigtwine.commons.models.dto.CoordinatesDTO;
import it.unimib.disco.bigtwine.commons.models.dto.DecodedLocationDTO;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import fr.dudie.nominatim.client.NominatimClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NominatimSyncExecutor implements GeoSyncExecutor {

    public String getBaseUrl() {
        return "https://nominatim.openstreetmap.org/";
    }

    public String getApiEmail() {
        return "fausto91@gmail.com";
    }

    protected NominatimClient getNominatimClient() {
        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);

        final String baseUrl = this.getBaseUrl();
        final String email = this.getApiEmail();

        return new JsonNominatimClient(baseUrl, httpClient, email);
    }

    @Override
    public String getExecutorId() {
        return "NominatimSyncExecutor";
    }

    @Override
    public DecodedLocation search(Location location) {
        NominatimClient client = this.getNominatimClient();
        String addressStr = location.getAddress();
        try {
            List<Address> addresses = client.search(addressStr);

            if (addresses.size() == 0) {
                return null;
            }

            Address address = addresses.get(0);

            return new DecodedLocationDTO(
                addressStr,
                new CoordinatesDTO(address.getLatitude(), address.getLongitude()),
                location.getTag()
            );
        }catch (IOException e) {
            return null;
        }
    }

    @Override
    public DecodedLocation[] search(Location[] locations) {
        List<DecodedLocation> addresses = new ArrayList<>();
        for (Location location : locations) {
            DecodedLocation addr = this.search(location);
            if (addr != null) {
                addresses.add(addr);
            }
        }
        return addresses.toArray(new DecodedLocation[0]);
    }
}
