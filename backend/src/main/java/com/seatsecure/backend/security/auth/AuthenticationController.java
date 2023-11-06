package com.seatsecure.backend.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seatsecure.backend.exceptions.user.RegistrationValidationException;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register-user")
    public ResponseEntity<AuthenticationResponse> registerUser(
        @Valid @RequestBody RegisterRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new RegistrationValidationException(bindingResult, "Validation error!");
        }
        return ResponseEntity.ok(service.register(request, false));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
        @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request, true));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
