package com.animesh.facecount.dto.user;

public record UserResponseDTO(
        String userid,
        String name,
        short yop,
        String department,
        String email,
        String role
) {
}
