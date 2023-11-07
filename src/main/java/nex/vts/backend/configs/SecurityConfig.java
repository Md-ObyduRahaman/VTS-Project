package nex.vts.backend.configs;

import nex.vts.backend.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] allowedRoutes = {
            "/api/private/v1/1/login",
            "/api/private/v1/1/refresh-token",
            "/api/private/v1/2/login",
            "/api/private/v1/2/refresh-token",
            "/api/private/v1/3/login",
            "/api/private/v1/3/refresh-token",
            "/api/private/v1/{userId}/{deviceType}/expense/list", /*todo-->testing purpose*/
            "/api/private/v1/{userId}/{deviceType}/vehicles", /*todo-->testing purpose*/
            "/api/private/v1/{userId}/{deviceType}/vehicle/details", /*todo-->testing purpose*/
            "/api/private/v1/{userId}/{deviceType}/vehicle-history" /*todo-->testing purpose*/
    };

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;
    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private Environment environment;

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(allowedRoutes).permitAll()
                .requestMatchers("/api/public/**").permitAll()
                //.requestMatchers("/api/public/login","/api/public/RefreshToken").permitAll()
                //.and()
                /*.authorizeHttpRequests()*/.requestMatchers("/api/private/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        String appActiveProfile = environment.getProperty("spring.profiles.active");

        if(appActiveProfile == null || appActiveProfile.equals("gp") || appActiveProfile.equals("gpdev")){
            return new MessageDigestPasswordEncoder("SHA-256");
        }else {
            return NoOpPasswordEncoder.getInstance();
        }
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
