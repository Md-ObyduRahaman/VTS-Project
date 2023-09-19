package nex.vts.backend.configs;

import nex.vts.backend.dbentities.VTS_LOGIN_USER;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoUserDetails implements UserDetails {

    private final String username;
    private final String password;

    private final boolean isAccountActive;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(VTS_LOGIN_USER userInfo) {
        username = userInfo.getUSERNAME();
        password = userInfo.getPASSWORD();
        isAccountActive = userInfo.getIS_ACCOUNT_ACTIVE() == 1;
        /*authorities= Arrays.stream(userInfo.getROLES().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountActive;
    }
}
