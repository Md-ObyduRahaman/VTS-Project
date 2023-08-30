package nex.vts.backend.Repository;

import nex.vts.backend.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    List<User> findAll();

    Optional<User> findByUserName(String userName);

    boolean findById(Integer userID);

    int save(User user);
}
