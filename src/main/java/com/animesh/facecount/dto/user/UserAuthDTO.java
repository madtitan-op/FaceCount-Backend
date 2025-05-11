package com.animesh.facecount.dto.user;

/**
 * Data Transfer Object for user authentication.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
public record UserAuthDTO(
        String userid,
        String password
) {
}
