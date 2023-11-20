package nex.vts.backend.repositories;

import nex.vts.backend.models.responses.Case1UserInfo;

import java.util.Optional;

public interface LoginUserInformation {
    Optional<Case1UserInfo> caseOneAccountInfo(Integer profileId, String userName, String password,String dynamicColumnName,Integer operatorid);
}
