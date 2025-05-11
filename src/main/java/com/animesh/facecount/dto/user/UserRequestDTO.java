package com.animesh.facecount.dto.user;

/**
 * Data Transfer Object for user requests.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
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
