package thequeuers.seatsecure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class AuthConfig {
    private UserDetailsService userDetailsService;

    public AuthConfig(UserDetailsService userSvc) {
        this.userDetailsService = userSvc;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // TODO

    }


}