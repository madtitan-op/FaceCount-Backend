package com.animesh.facecount.dto.faculty;

/**
 * DTO for Faculty creation or update requests.
 */
public record FacultyRequestDTO(
    Long faculty_id,
    String email,
    String name,
    String password,
    String role,
    String department
) {
}
