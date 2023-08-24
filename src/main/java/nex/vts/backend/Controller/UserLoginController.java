package nex.vts.backend.Controller;

import nex.vts.backend.Model.User;
import nex.vts.backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserLoginController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        System.out.println("Sojib");
        return userRepo.findAll();
    }
}
