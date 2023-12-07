package nex.vts.backend.repositories;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;

import java.util.Optional;

public interface AddExpense_Repo {
    Object addExpense(String userId,String groupId,String expenseId,String dateTime,String schemaName,
                      String amount, String description,Integer expenseId2,Integer deptId);


    /*todo
    *  INSERT INTO nex_all_expenditure (
                    USER_ID, GROUPID,
                    EXPENSE_ID, DATE_TIME, AMOUNT, DESCRIPTION, EXPENSE_ID_N,
                    DEPT_ID
                    )
                VALUES (
                    '$_vehID','$_compID',
                    '$_expHeader','$_expDate','$_expAmount','$_expNote', '$_expHeader',
                    '$_deptID'
                    );*/
}
