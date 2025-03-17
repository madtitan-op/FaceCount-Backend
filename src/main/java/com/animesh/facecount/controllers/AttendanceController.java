package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.AttendanceMarkDTO;
import com.animesh.facecount.dto.GetAttendanceRecDTO;
import com.animesh.facecount.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("mark-attendance")
    public GetAttendanceRecDTO addPresence(@RequestBody AttendanceMarkDTO markDTO) {
        return attendanceService.markAttendance(markDTO);
    }

}
