package com.animesh.facecount.dto.attendance;

/**
 * Data Transfer Object for marking attendance.
 *
 * @version 1.0
 */
public record AttendanceRequestDTO(
        Long userId,
        String status,
        String role,
        long marker_id
) {
}

