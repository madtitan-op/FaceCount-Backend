package com.animesh.facecount.dto;

/**
 * Data Transfer Object for user authentication.
 *
 * @version 1.0
 */
public record UserAuthDTO(
        String userid,
        String password
) {
}
