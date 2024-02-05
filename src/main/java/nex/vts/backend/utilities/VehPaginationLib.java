package nex.vts.backend.utilities;

public class VehPaginationLib {

//    public static boolean isValidPagination(int _offSet, int _rowLimit) {
//
//        if (is_Valid_Limit(_rowLimit)) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

    public static boolean is_Valid_Limit(int _rowLimit) {

        if (!String.valueOf(_rowLimit).equals("") && (_rowLimit < 0 || _rowLimit > 100)) {
            return false;
//            "Invalid or incorrect limit!";
//            limit is out of range! Range should be 0 to 100
        } else {
            return true;
        }

    }


    public static boolean is_Valid_OffSet(int _offSet) {

        if (String.valueOf(_offSet).equals("") || _offSet < 0) {
            return false;
//            "Invalid or incorrect offset!";
        } else {
            return true;
        }

    }


}
