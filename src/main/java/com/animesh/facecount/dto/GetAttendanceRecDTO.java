package com.animesh.facecount.dto;

import java.time.LocalDate;

public record GetAttendanceRecDTO(
        String userId,
//        int courseId,
        LocalDate date,
        String status
) {
}
