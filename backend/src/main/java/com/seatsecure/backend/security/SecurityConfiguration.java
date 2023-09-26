package com.seatsecure.backend.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.seatsecure.backend.configs.CorsConfig;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    
                .cors(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(r -> r
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/users")).hasRole(Role.ADMIN.name())
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/register-user")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/authenticate")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/register-admin")).hasRole(Role.ADMIN.name())

                    //.requestMatchers(new AntPathRequestMatcher("/api/v1/users")).hasRole(Role.ADMIN.name())// this is only use for uniform access, if method based have to look into the controller
                    .anyRequest().authenticated()
                )
                
                .sessionManagement((sessionManagement) ->
                    sessionManagement
                        // .sessionConcurrency((sessionConcurrency) ->
                        // sessionConcurrency
                        // .maximumSessions(1)
                        // .expiredUrl("/login?expired")
                        // )

                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .headers((headers) -> headers.disable())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                

        return http.build();
    }


    // private Customizer<CorsConfigurer<HttpSecurity>> withDefaults() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(Arrays.asList("*"));
    //     configuration.setAllowedMethods(Arrays.asList("*"));
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return (Customizer<CorsConfigurer<HttpSecurity>>) source;
    // }

}
