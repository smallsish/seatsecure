package com.seatsecure.backend.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seatsecure.backend.entities.User;
<<<<<<< HEAD
import com.seatsecure.backend.entities.enums.Role;
=======
import com.seatsecure.backend.exceptions.UsernameAlreadyExistsException;
>>>>>>> 26b805ec9d4b5c15bedac112c3d3a8018ced3761
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
                .role(isAdminAccount ? Role.ADMIN : Role.USER)
                .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
            
        } else {
            throw new UsernameAlreadyExistsException("The username '"+ uName + "' already exists.");
        }
>>>>>>> 26b805ec9d4b5c15bedac112c3d3a8018ced3761
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
            .build();
    }
}
