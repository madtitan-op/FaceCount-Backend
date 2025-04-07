package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.attendance.AttendanceMarkDTO;
import com.animesh.facecount.dto.attendance.GetAttendanceRecDTO;
import com.animesh.facecount.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling attendance-related requests.
 * @author Animesh Mahata
 * @version 1.0
 * @since 17-03-2025
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Marks the attendance for a user.
     *
     * @param markDTO the attendance mark data transfer object
     * @return the attendance record data transfer object
     */
    @PostMapping("mark")
    public GetAttendanceRecDTO addPresence(@RequestBody AttendanceMarkDTO markDTO) {
        return attendanceService.markAttendance(markDTO);
    }

    /**
     * Fetches the attendance records for a user for a specific month and year.
     *
     * @param month the month for which to fetch attendance records
     * @param year the year for which to fetch attendance records
     * @param userId the ID of the user for whom to fetch attendance records
     * @return a ResponseEntity containing a list of attendance record data transfer objects
     */
    @GetMapping("fetch/{month}/{year}/{userId}")
    public ResponseEntity<List<GetAttendanceRecDTO>> fetchAttendanceByMonth(
            @PathVariable int month,
            @PathVariable int year,
            @PathVariable String userId) {

        System.out.printf("Attendance Controller:   M: %d Y: %d UID: %s", month, year, userId);
        List<GetAttendanceRecDTO> attendanceRecord = attendanceService.fetchAttendanceByMonth(month, year, userId);

        if (!attendanceRecord.isEmpty()) {
            return new ResponseEntity<>(attendanceRecord, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
