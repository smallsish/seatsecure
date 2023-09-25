package com.seatsecure.backend.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.User;
import com.seatsecure.backend.exceptions.UsernameAlreadyExistsException;
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

    public AuthenticationResponse register(RegisterRequest request) {
        String uName = request.getUsername();
        if (!userRepository.findByUsername(uName).isPresent()) {
            var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .username(uName)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
            
        } else {
            throw new UsernameAlreadyExistsException("The username '"+ uName + "' already exists.");
        }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
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
            .build();
    }
}
