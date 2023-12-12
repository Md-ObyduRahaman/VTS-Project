package nex.vts.backend.configs;


import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import nex.vts.backend.repoImpl.RepoVtsLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RepoVtsLoginUser repoVtsLoginUser;

    @Autowired
    Environment environment;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<VTS_LOGIN_USER> userInfo = repoVtsLoginUser.findByUserName(username,environment.getProperty("application.profiles.shcemaName"));
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

}
