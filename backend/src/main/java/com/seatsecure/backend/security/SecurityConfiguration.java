package com.seatsecure.backend.security;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.seatsecure.backend.entities.Role;

import com.seatsecure.backend.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // .authorizeHttpRequests(auth -> auth
                //         .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                //         .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll().authenticated()
                //         //.requestMatchers(new AntPathRequestMatcher("/api/v1/users")).permitAll().anyRequest().hasRole("ADMIN")
                //         //.requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("USER")
                        
                // )
                .authorizeHttpRequests(requests -> requests
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    //.requestMatchers(new AntPathRequestMatcher("/api/v1/users")).hasRole("USER_ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
                    //.requestMatchers("/api/v1/users").hasRole("USER")
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
                .csrf((csrf) -> csrf.disable());
        return http.build();
    }
}
