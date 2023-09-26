package com.seatsecure.backend.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.seatsecure.backend.configs.CorsConfig;
import com.seatsecure.backend.entities.Permission;
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
                .cors(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(r -> r
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()

                        // .requestMatchers(new
                        // AntPathRequestMatcher("/api/v1/users")).hasRole(Role.ADMIN.name())// the
                        // entire page
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/users")).hasRole(Role.ADMIN.name())
                        // the resepctive HTTP commands
                        // .requestMatchers(new AntPathRequestMatcher("/api/v1/users/**",
                        // "HttpMethod.GET")).hasAuthority(Permission.ADMIN_READ.name())
                        // .requestMatchers(new AntPathRequestMatcher("/api/v1/users/**",
                        // "HttpMethod.POST")).hasAuthority(Permission.ADMIN_CREATE.name())
                        // .requestMatchers(new AntPathRequestMatcher("/api/v1/users/**",
                        // "HttpMethod.PUT")).hasAuthority(Permission.ADMIN_UPDATE.name())
                        // .requestMatchers(new AntPathRequestMatcher("/api/v1/users/**",
                        // "HttpMethod.DELETE")).hasAuthority(Permission.ADMIN_DELETE.name())

                        // .requestMatchers("/api/v1/admins").hasAnyRole(Role.ADMIN.name())

                        // .requestMatchers(HttpMethod.GET,
                        // "/api/v1/admins/**").hasAuthority(Permission.ADMIN_READ.name())
                        // .requestMatchers(HttpMethod.POST,
                        // "/api/v1/admins/**").hasAuthority(Permission.ADMIN_CREATE.name())
                        // .requestMatchers(HttpMethod.PUT,
                        // "/api/v1/admins/**").hasAuthority(Permission.ADMIN_UPDATE.name())
                        // .requestMatchers(HttpMethod.DELETE,
                        // "/api/v1/admins/**").hasAuthority(Permission.ADMIN_DELETE.name())
                        .anyRequest().authenticated())

                .sessionManagement((sessionManagement) -> sessionManagement
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
