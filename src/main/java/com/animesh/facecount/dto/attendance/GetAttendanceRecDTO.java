package com.animesh.facecount.dto.attendance;

import java.time.LocalDate;

public record GetAttendanceRecDTO(
        String userId,
//        int courseId,
        LocalDate date,
        String status
) {
}
