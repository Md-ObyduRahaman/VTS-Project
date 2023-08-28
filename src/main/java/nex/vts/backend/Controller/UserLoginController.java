package nex.vts.backend.Controller;

import nex.vts.backend.Model.AuthRequest;
import nex.vts.backend.Model.User;
import nex.vts.backend.Repository.UserRepo;
import nex.vts.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserLoginController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('User')")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    @GetMapping("/users/{userName}")
    @PreAuthorize("hasAuthority('User')")
    public Optional<User> getUsersById(@PathVariable String userName) {
        return userRepo.findByUserName(userName);
    }

    @PostMapping("/new")
    public Integer addNewUser(@RequestBody User userInfo) {
        return userRepo.save(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }
}
