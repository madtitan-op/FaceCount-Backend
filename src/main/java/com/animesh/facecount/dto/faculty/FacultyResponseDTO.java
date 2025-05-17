package com.animesh.facecount.dto.faculty;

/**
 * DTO for Faculty response data.
 */
public record FacultyResponseDTO(
    Long faculty_id,
    String email,
    String name,
    String role,
    String department
) {
}
