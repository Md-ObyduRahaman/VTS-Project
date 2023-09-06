package nex.vts.backend.repositories;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;

import java.util.Optional;

public interface VtsLoginUserRepo {
    Optional<VTS_LOGIN_USER> findByUserName(String userName);

}
