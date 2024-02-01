package nex.vts.backend.utilities;

public class ExtractLocationLib {

    public static String get_Location(String _lat, String _lon) {

        String res = get_Location_HTTP(_lat, _lon);

        return res; //_lat + "," + _lon;
    }


    public static String get_Location_HTTP(String _lat, String _lon) {

        return _lat + "," + _lon;
    }



    private static String _XY() {

        return null;
    }



}
