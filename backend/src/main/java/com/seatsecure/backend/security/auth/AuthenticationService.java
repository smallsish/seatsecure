package com.seatsecure.backend.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.entities.enums.Role;
import com.seatsecure.backend.exceptions.others.CurrentUserException;
import com.seatsecure.backend.exceptions.others.UsernameAlreadyExistsException;
import com.seatsecure.backend.repositories.UserRepository;
import com.seatsecure.backend.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request, Boolean isAdminAccount) {
        String uName = request.getUsername().toLowerCase();
        if (!userRepository.findByUsername(uName).isPresent()) {
            var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .gender(request.getGender())
                .phoneNumber(Integer.parseInt(request.getPhoneNumber()))
                .username(uName)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(isAdminAccount ? Role.ADMIN : Role.USER)
                .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(request.getUsername())
                .id(user.getId())
                .build();
            
        } else {
            throw new UsernameAlreadyExistsException(uName);
        }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate( // Throws an error if authentication fails
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        var user = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .username(user.getUsername())
            .id(user.getId())
            .build();
    }



    // Retrieve the UserDetails in the current security context
    public UserDetails getCurrentUserDetails() {
        try {
            SecurityContext sc = SecurityContextHolder.getContext();
            Object p = sc.getAuthentication().getPrincipal();
            if (p != null && p instanceof UserDetails) {
                return (UserDetails) p;
            }

        } catch (RuntimeException e) {
            throw new CurrentUserException();
        }

        throw new CurrentUserException();
            
    }

    // Check if the username in the parameter is that of the user in the current security context
    public Boolean isCurrentUser(String username) {
        UserDetails ud = getCurrentUserDetails();
        return ud.getUsername().toLowerCase().equals(username.toLowerCase());
    }
}
