package com.animesh.facecount.dto.user;

public record UserRequestDTO(
        String userid,
        String name,
        short yop,
        String department,
        String email,
        String password,
        String role
) {
}
