package com.animesh.facecount.dto.attendance;

import java.time.LocalDate;

/**
 * Data Transfer Object for getting attendance records.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
public record GetAttendanceRecDTO(
        String userId,
        LocalDate date,
        String status
) {
}
