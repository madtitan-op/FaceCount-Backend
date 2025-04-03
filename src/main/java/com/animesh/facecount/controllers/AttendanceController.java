package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.attendance.AttendanceMarkDTO;
import com.animesh.facecount.dto.attendance.GetAttendanceRecDTO;
import com.animesh.facecount.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("mark")
    public GetAttendanceRecDTO addPresence(@RequestBody AttendanceMarkDTO markDTO) {
        return attendanceService.markAttendance(markDTO);
    }

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
