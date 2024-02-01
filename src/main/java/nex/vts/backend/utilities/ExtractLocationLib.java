package nex.vts.backend.utilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ExtractLocationLib {

    public static String get_Location(String _lat, String _lon)  {

        String res = null;


            res = get_Location_HTTP(_lat, _lon);


        return res; //_lat + "," + _lon;
    }


    public static String get_Location_HTTP(String _lat, String _lon)  {


        String areaValue=null;

        try {

        // Starting fetch data
        HttpClient httpClient = HttpClients.createDefault();
        String apiUrl = "https://vts2.m2mbd.com/m_backend/rev_geocoder.php?lat=" + _lat + "&lon=" + _lon;

        HttpGet httpGet = new HttpGet(apiUrl);
        //  httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String xmlResponse  = EntityUtils.toString(entity);



            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(xmlResponse.getBytes("UTF-8"));
            Document doc = builder.parse(input);

            // Get the value of the 'area' attribute
            NodeList markerList = doc.getElementsByTagName("marker");
            if (markerList.getLength() > 0) {
                Element markerElement = (Element) markerList.item(0);
                 areaValue = markerElement.getAttribute("area");
                System.out.println("Area Value: " + areaValue);
            } else {
                System.out.println("No marker element found in the XML response.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaValue;
    }


    private static String _XY() {

        return null;
    }


}
