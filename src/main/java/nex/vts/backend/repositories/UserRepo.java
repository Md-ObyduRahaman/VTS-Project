package nex.vts.backend.repositories;

import nex.vts.backend.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    List<User> findAll();

    Optional<User> findByUserName(String userName);

    boolean findById(Integer userID);

    int save(User user);
}
