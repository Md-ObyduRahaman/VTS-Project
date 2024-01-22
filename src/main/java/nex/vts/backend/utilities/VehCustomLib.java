package nex.vts.backend.utilities;

public class VehCustomLib {


    /**
     * |--------------------------------------------------------------------------
     * | Manage Active/Inactive
     * |--------------------------------------------------------------------------
     */
    public static String get_VehStat(int xStat) {

        String _res = null;

        if (xStat == 1) {
            _res = "Active";
        } else {
            _res = "Inactive";
        }

        return _res;
    }


    /**
     |--------------------------------------------------------------------------
     | Default Icon Name
     |--------------------------------------------------------------------------
     */
//    public static function getDefaultIconName($iconType) {
//
//        if ($iconType == 2) {
//            $_defaultName = "Vessel";
//        } else {
//            $_defaultName = "Vehicle";
//        }
//
//        return $_defaultName;
//    }


    /**
     |--------------------------------------------------------------------------
     | Set Engine Speed [Check engine on or off]
     |--------------------------------------------------------------------------
     */
//    public static function set_EnginSpeed($position, $speed) {
//
//        if ($position == "OFF") {
//            $_Speed = "0.0";
//        } else {
//            $_Speed = $speed;
//        }
//
//        return $_Speed;
//    }


//    public static function set_EnginStat($position) {
//
//        if ($position == "OFF") {
//            $_result = "STOPPED";
////            $_result = "Please, raise feedback";
//        } else {
//            $_result = 'RUNNING';
////            $_result = 'LIVE';
//        }
//
//        return $_result;
//    }



    /**
     |--------------------------------------------------------------------------
     | Set Week-Day
     |--------------------------------------------------------------------------
     */
//    public static function set_WeekDay($strID) {
//
//        if ($strID == "8") {
//            $_res = "All Day";
//
//        } elseif ($strID == "1") {
//            $_res = "Saturday";
//
//        } elseif ($strID == "2") {
//            $_res = "Sunday";
//
//        } elseif ($strID == "3") {
//            $_res = "Monday";
//
//        } elseif ($strID == "4") {
//            $_res = "Tuesday";
//
//        } elseif ($strID == "5") {
//            $_res = "Wednesday";
//
//        } elseif ($strID == "6") {
//            $_res = "Thursday";
//
//        } elseif ($strID == "7") {
//            $_res = "Friday";
//        }
//
//        return $_res;
//    }


}
