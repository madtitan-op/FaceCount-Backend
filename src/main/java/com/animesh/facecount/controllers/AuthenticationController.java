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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

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
