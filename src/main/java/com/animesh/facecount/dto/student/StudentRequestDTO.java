package com.animesh.facecount.dto.student;

public record StudentRequestDTO(
        Long student_id,
        String name,
        short yop,
        String department,
        String email,
        String password,
        String role
) {
}
