package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.user.UserAuthDTO;
import com.animesh.facecount.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests.
 * @author Animesh Mahata
 * @version 1.0
 * @since 04-04-2025
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    /**
     * Endpoint for user login.
     *
     * @param authDTO the user authentication data transfer object containing userid and password
     * @return a JWT token if authentication is successful, otherwise a failure message
     */
    @PostMapping("login")
    public String login(@RequestBody UserAuthDTO authDTO) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.userid(), authDTO.password()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authDTO.userid());
        }

        return "LOGIN UNSUCCESSFUL!!";
    }

}
