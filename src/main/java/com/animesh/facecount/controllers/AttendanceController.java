package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.attendance.AttendanceRequestDTO;
import com.animesh.facecount.dto.attendance.AttendanceResponseDTO;
import com.animesh.facecount.entities.StudentPrincipal;
import com.animesh.facecount.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling attendance-related requests.
 *
 * @version 1.0
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
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY', 'SYSTEM')")
    @PostMapping("admin/mark")
    public ResponseEntity<AttendanceResponseDTO> addPresence(@RequestBody AttendanceRequestDTO markDTO) {
        try {
            return new ResponseEntity<>(attendanceService.markAttendance(markDTO), HttpStatus.OK);
        }catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Fetches the attendance records for a user for a specific month and year.
     *
     * @param month the month for which to fetch attendance records
     * @param year the year for which to fetch attendance records
     * @param userId the ID of the user for whom to fetch attendance records
     * @return a ResponseEntity containing a list of attendance record data transfer objects
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'SYSTEM', 'FACULTY', 'STUDENT')")
    @GetMapping("fetch/{month}/{year}")
    public ResponseEntity<List<AttendanceResponseDTO>> fetchAttendanceByMonth(
            @PathVariable int month,
            @PathVariable int year,
            @RequestParam Long userId,
            Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = Long.parseLong(userDetails.getUsername());

        if (userDetails instanceof StudentPrincipal && !currentUserId.equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<AttendanceResponseDTO> attendanceRecord = attendanceService.fetchAttendanceByMonth(month, year, userId);

        if (!attendanceRecord.isEmpty()) {
            return new ResponseEntity<>(attendanceRecord, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Fetches the attendance records for a user for a specific date.
     *
     * @param day the day of the month for which to fetch attendance
     * @param month the month for which to fetch attendance records
     * @param year the year for which to fetch attendance records
     * @return a ResponseEntity containing a list of attendance record data transfer objects
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'SYSTEM', 'FACULTY')")
    @GetMapping("fetch/{day}/{month}/{year}")
    public ResponseEntity<List<AttendanceResponseDTO>> fetchAttendanceByDay(
            @PathVariable int day,
            @PathVariable int month,
            @PathVariable int year
    ) {
        List<AttendanceResponseDTO> attendanceRecord = attendanceService.fetchAttendanceByDay(day, month, year);

        if (!attendanceRecord.isEmpty()) {
            return new ResponseEntity<>(attendanceRecord, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
