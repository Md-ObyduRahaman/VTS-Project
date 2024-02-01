package nex.vts.backend.utilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ExtractLocationLib {

    public static String get_Location(String _lat, String _lon) {

        String res = null;

        try {
            res = get_Location_HTTP(_lat, _lon);
        } catch (IOException ex) {
            System.out.println("Error in Location-HTTP");
        }

        return res; //_lat + "," + _lon;
    }


    public static String get_Location_HTTP(String _lat, String _lon) throws IOException {

        // Starting fetch data
        HttpClient httpClient = HttpClients.createDefault();
        String apiUrl = "https://vts2.m2mbd.com/m_backend/rev_geocoder.php?lat=" + _lat + "&lon=" + _lon;

        HttpGet httpGet = new HttpGet(apiUrl);
        //  httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String area = EntityUtils.toString(entity);

        return area;
    }


    private static String _XY() {

        return null;
    }


}
