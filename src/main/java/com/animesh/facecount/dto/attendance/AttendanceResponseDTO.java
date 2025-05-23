package com.animesh.facecount.dto.attendance;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object for getting attendance records.
 *
 * @version 1.0
 */
public record AttendanceResponseDTO(
        String name,
        Long userId,
        String status,
        LocalDate date,
        LocalTime time,
        Long marked_by_faculty_id,
        Integer marked_by_system_id
) {
}
