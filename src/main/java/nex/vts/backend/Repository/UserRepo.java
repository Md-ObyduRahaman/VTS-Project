package nex.vts.backend.Repository;

import nex.vts.backend.Model.User;

import java.util.List;

public interface UserRepo {
    List<User> findAll();
}
