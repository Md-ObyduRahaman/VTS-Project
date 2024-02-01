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
import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.Math.cos;


public class ExtractLocationLib {

    private static String poi_Name = "";
    private static String road_Name = "";
    private static String thana_Name = "";



    public static String get_Location(String _lat, String _lon) {

        String res = null;
        res = get_Location_HTTP(_lat, _lon);

        return res; //_lat + "," + _lon;
    }


    public static String get_Location_HTTP(String _lat, String _lon) {

        String areaValue = null;

        try {
            // Starting fetch data
            HttpClient httpClient = HttpClients.createDefault();
            String apiUrl = "https://vts2.m2mbd.com/m_backend/rev_geocoder.php?lat=" + _lat + "&lon=" + _lon;

            HttpGet httpGet = new HttpGet(apiUrl);
            //  httpGet.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String xmlResponse = EntityUtils.toString(entity);


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
            //
        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaValue;
    }


    /**
     * |==========================================================================
     * | SET:: POINT-IN-POLYGON
     * [HELP:: https://www.algorithms-and-technologies.com/point_in_polygon/java]
     * |==========================================================================
     *
     * @param p_Lat
     * @param p_Lon
     * @param p_polyCount
     * @param polyX
     * @param polyY
     * @return
     */
    public static Boolean pointInPolygon(Double p_Lat, Double p_Lon, int p_polyCount, String[] polyX, String[] polyY) {
        //SET:: Value in Variable ---
        int i = 0;
        int polySides = p_polyCount;
        int j = polySides - 1;
        boolean $oddNodes = false;
        //
        //
        for (i = 0; i < polySides; i++) {
            //
            if (Double.parseDouble(polyY[i]) < p_Lon && Double.parseDouble(polyY[j]) >= p_Lon || Double.parseDouble(polyY[j]) < p_Lon && Double.parseDouble(polyY[i]) >= p_Lon) {
                //
                if (Double.parseDouble(polyX[i]) + (p_Lon - Double.parseDouble(polyY[i])) / (Double.parseDouble(polyY[j]) - Double.parseDouble(polyY[i])) * (Double.parseDouble(polyX[j]) - Double.parseDouble(polyX[i])) < p_Lat) {
                    $oddNodes = !$oddNodes;
                }
            }

            j = i;

        }//end for-loop

        return $oddNodes;
    }


    /**
     * |==========================================================================
     * | SET:: STRING TO ARRAY
     * |==========================================================================
     *
     * @param strText
     * @param separator
     */
    private static String[] getCSVValues(String strText, String separator) {
        //SET:: Value in Variable ---
        String[] elements = strText.split(",");

//        for (int i = 0; i < elements.length; i++) {
//            System.out.println(i+"  "+elements[i]);
//        }

        return elements;  //Arrays.toString(
    }


    /**
     * |==========================================================================
     * | SET:: DISTANCE CALULATION
     * |==========================================================================
     *
     * @param p_Lat1
     * @param p_Lon1
     * @param p_Lat2
     * @param p_Lon2
     */
    public static Double distance(Double p_Lat1, Double p_Lon1, Double p_Lat2, Double p_Lon2) {
        Double xLat1 = (p_Lat1 / 57.29577951);
        Double xLon1 = (p_Lon1 / 57.29577951);
        Double xLat2 = (p_Lat2 / 57.29577951);
        Double xLon2 = (p_Lon2 / 57.29577951);
        //convert all to radians: degree/57.29577951

        Double $dist;

        if (xLat1.equals(xLat2) && xLon1.equals(xLon2)) {
            $dist = Double.valueOf(0);
            //
        } else if ((sin(xLat1) * sin(xLat2) + cos(xLat1) * cos(xLat2) * cos(xLon1 - xLon2)) > 1) {
            $dist = 3963.1 * acos(1); // solved a prob I ran into.  I haven't fully analyzed it yet
            //
        } else {
            $dist = 3963.1 * acos(sin(xLat1) * sin(xLat2) + cos(xLat1) * cos(xLat2) * cos(xLon1 - xLon2));
        }

        return ($dist * 1.609344);
    }


    /**
     * |==========================================================================
     * | SET:: POI DIRECTION
     * |==========================================================================
     *
     * @param p_lat1
     * @param p_lon1
     * @param p_lat2
     * @param p_lon2
     */
    private static String poi_direction(Double p_lat1, Double p_lon1, Double p_lat2, Double p_lon2) {
        String _DirectionTxt = "";
        Double x_Head = atan2(sin(p_lon2 - p_lon1) * cos(p_lat2), (cos(p_lat1) * sin(p_lat2)) - (sin(p_lat1) * cos(p_lat2) * cos(p_lon2 - p_lon1)));

        if (x_Head > -0.0545363682794 && x_Head < 0.0545363682794) {
            _DirectionTxt = "North";

        } else if (x_Head > 0.0545363682794 && x_Head < 0.998515526487) {
            _DirectionTxt = "North East";

        } else if ((x_Head < 3.1418 && x_Head > 3.07226) || (x_Head > -3.1355 || x_Head < -3.09206887218)) {
            _DirectionTxt = "South";

        } else if (x_Head > 0.998515526487 && x_Head < 2.38913476576) {
            _DirectionTxt = "East";

        } else if (x_Head > 2.38913476576 && x_Head < 3.07226) {
            _DirectionTxt = "South East";

        } else if (x_Head > -2.47305285252 && x_Head < -0.73857152973) {
            _DirectionTxt = "West";

        } else if (x_Head < -0.0545363682794 && x_Head > -0.73857152973) {
            _DirectionTxt = "North West";

        } else if (x_Head < -2.47305285252 && x_Head > -3.09206887218) {
            _DirectionTxt = "South West";
        }

        return _DirectionTxt;
    }


    /**
     * |==========================================================================
     * | SET:: POI DIRECTION
     * |==========================================================================
     *
     * @param arr1
     * @param arr2
     * @param arr3
     * @return
     */
    private static void set_ArrayGroup_Sort(ArrayList<Double> arr1, ArrayList<String> arr2, ArrayList<String> arr3) {
        int n = arr1.size();
        Double temp;
        String temp1 = "", temp2 = "";

        //SET:: Array Sorting ---- /start/
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr1.get(j - 1) > arr1.get(j)) {
                    //swap elements
                    temp = arr1.get(j - 1);
                    arr1.set((j - 1), arr1.get(j));
                    arr1.set(j, temp);

                    temp1 = arr2.get(j - 1);
                    arr2.set((j - 1), arr2.get(j));
                    arr2.set(j, temp1);

                    temp2 = arr3.get(j - 1);
                    arr3.set((j - 1), arr3.get(j));
                    arr3.set(j, temp2);
                }

            }

        }
        //SET:: Array Sorting ---- /end/


        if (!"".equals(arr1.get(0).toString().trim())
                && !"".equals(arr2.get(0).trim())
                && !"".equals(arr3.get(0).trim())) {

            Double distance = Double.valueOf(arr1.get(0).toString().trim());
            String direction = arr2.get(0).trim();
            String location = arr3.get(0).trim();

            poi_Name = String.format("%.3f", distance) + " Km " + direction + " From " + location;

        } else {
            poi_Name = "";
        }


//        for (int i = 0; i < arr1.size(); i++) {
//            System.out.println(arr1.get(i).toString().trim());
//        }
//        System.out.println("\n");


//        for (int i = 0; i < arr1.size(); i++) {
//            System.out.print(arr2.get(i).toString().trim());
//            System.out.println("");
//        }
//        System.out.println("\n");


//        for (int i = 0; i < arr1.size(); i++) {
//            System.out.print(arr3.get(i).toString().trim());
//            System.out.println("");
//        }


//        Integer[] array = list.toArray(new Integer[0]);
//
//        // Print the array
//        for (Integer n : array) {
//            System.out.println(n);
//        }


    }


}
