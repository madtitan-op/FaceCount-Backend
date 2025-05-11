package com.animesh.facecount.dto.user;

/**
 * Data Transfer Object for user responses.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
public record UserResponseDTO(
        String userid,
        String name,
        short yop,
        String department,
        String email,
        String role
) {
}
