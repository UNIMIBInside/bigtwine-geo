package it.unimib.disco.bigtwine.geo.decoder.executors;

import fr.dudie.nominatim.client.JsonNominatimClient;
import it.unimib.disco.bigtwine.commons.models.Address;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.dudie.nominatim.client.NominatimClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NominatimSyncExecutor implements GeoSyncExecutor {

    public String getBaseUrl() {
        return null;
    }

    public String getApiEmail() {
        return null;
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
    public Address search(String location) {
        NominatimClient client = this.getNominatimClient();
        try {
            List<fr.dudie.nominatim.model.Address> addresses = client.search(location);
            if (addresses.size() == 0) {
                return null;
            }

            return new Address(location, new Address.Coordinate(
                addresses.get(0).getLatitude(),
                addresses.get(0).getLongitude())
            );
        }catch (IOException e) {
            return null;
        }
    }

    @Override
    public Address[] search(String[] locations) {
        List<Address> addresses = new ArrayList<>();
        for (String location : locations) {
            Address addr = this.search(location);
            if (addr != null) {
                addresses.add(addr);
            }
        }
        return addresses.toArray(new Address[0]);
    }
}
