package nex.vts.backend.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class VtsDateTimeLib {

    public static String get_DateFormatter(String xGiverDate) {

        String outputDateString = null;
        // Specify the input date format
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");

        // Specify the desired output date format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {

            if (xGiverDate.equals("N/A") || xGiverDate.equals("")) {
                outputDateString = xGiverDate;
            } else {
                // Parse the input date string
                Date date = inputDateFormat.parse(xGiverDate);

                // Format the date to the desired output format
                outputDateString = outputDateFormat.format(date);
            }

            // Print the result
            //System.out.println(outputDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return outputDateString;
    }




//    public static String get_DateFormatter(String xGiverDate) {
//
//        String resDate = null;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            resDate = String.valueOf(sdf.parse(xGiverDate));
//            System.out.println(resDate);
//        } catch (
//                ParseException e) {
//            e.printStackTrace();
//        }
//
//        return  resDate;
//    }


    /**
     |--------------------------------------------------------------------------
     | Set Data Add/SubTract in
     |--------------------------------------------------------------------------
     */
//    public static function date_addition($ndate) {
//        return date('Y-m-d H:i:s', strtotime('+2 hour', strtotime($ndate)));
//    }
//
//    public static function date_addition_one($ndate) {
//        return date('Y-m-d H:i:s', strtotime('+1 hour', strtotime($ndate)));
//    }
//
//    public static function date_subtract_four($ndate) {
//        return date('Y-m-d H:i:s', strtotime('-2 hour', strtotime($ndate)));
//    }
//
//    public static function date_subtract_six($ndate) {
//        return date('Y-m-d H:i:s', strtotime('-4 hour', strtotime($ndate)));
//    }
//
//    public static function date_subtract_tw($ndate) {
//        return date('Y-m-d H:i:s', strtotime('-10 hour', strtotime($ndate)));
//    }


}
