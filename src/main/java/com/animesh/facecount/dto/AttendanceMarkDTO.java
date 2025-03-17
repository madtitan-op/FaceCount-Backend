package com.animesh.facecount.dto;

public record AttendanceMarkDTO(
        long userId,
        Integer courseId,
        String status
) {
}
