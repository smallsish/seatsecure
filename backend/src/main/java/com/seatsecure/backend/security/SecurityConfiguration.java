package com.seatsecure.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import com.seatsecure.backend.services.LogoutService;

import com.seatsecure.backend.entities.enums.Role;
import com.seatsecure.backend.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(r -> r
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()

                    //.requestMatchers(new AntPathRequestMatcher("/api/v1/users")).hasRole(Role.ADMIN.name())// this is only use for uniform access, if method based have to look into the controller
                    .anyRequest().authenticated()
                )
                
                .sessionManagement((sessionManagement) ->
                    sessionManagement
                        // .sessionConcurrency((sessionConcurrency) ->
                        //         sessionConcurrency
                        //                 .maximumSessions(1)
                        //                 .expiredUrl("/login?expired")
                        //                 )

                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .headers((headers) -> headers.disable())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutService)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
                // .logoutUrl("/api/v1/auth/logout")
                // .addLogoutHandler(logoutService)
                // .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                
        return http.build();
    }
}
