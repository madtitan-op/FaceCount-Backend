package com.animesh.facecount.dto.student;

public record StudentResponseDTO(
        Long student_id,
        String name,
        short yop,
        String department,
        String email,
        String role
) {
}
