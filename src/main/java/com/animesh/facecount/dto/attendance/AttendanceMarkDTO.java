package com.animesh.facecount.dto.attendance;

/**
 * Data Transfer Object for marking attendance.
 * 
 * @author Animesh Mahata
 * @version 1.0
 */
public record AttendanceMarkDTO(
        String userId,
//        Integer courseId,
        String status
) {
}

