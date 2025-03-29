package com.animesh.facecount.services;

import com.animesh.facecount.dto.AttendanceMarkDTO;
import com.animesh.facecount.dto.GetAttendanceRecDTO;
import com.animesh.facecount.entities.AttendanceRecord;
import com.animesh.facecount.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;

//    AttendanceRecord --> GetAttendanceRecDTO
    public GetAttendanceRecDTO attendanceRecordToGetAttendanceDTO(AttendanceRecord record) {
        return new GetAttendanceRecDTO(
                record.getUserId(),
                /*record.getCourseId(),*/
                record.getDate(),
                record.getStatus()
        );
    }

//    AttendanceMarkDTO --> AttendanceRecord
    public AttendanceRecord attendanceMarkDTOToAttendanceRecord(AttendanceMarkDTO markDTO) {
        AttendanceRecord record = new AttendanceRecord();
        record.setUserId(markDTO.userId());
//        record.setCourseId(markDTO.courseId());
        record.setDate(LocalDate.now());
        record.setStatus(markDTO.status());
        record.setTimestamp(LocalDateTime.now());
        return record;
    }

//    Mark Attendance in Database
    public GetAttendanceRecDTO markAttendance(AttendanceMarkDTO markDTO) {
        AttendanceRecord record = attendanceRepo.save(attendanceMarkDTOToAttendanceRecord(markDTO));
        return attendanceRecordToGetAttendanceDTO(record);
    }

//Get attendance of certain student for a month
    public List<GetAttendanceRecDTO> fetchAttendanceByMonth(int month, int year, String userId) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, 31);

        System.out.printf("Attendance Service:  M: %d Y: %d UID: %s", month, year, userId);
        System.out.printf("Attendance Service:  S: %tF E: %tF", start, end);

        List<AttendanceRecord> attendanceRecords = attendanceRepo.findAttendanceRecordsByUserIdAndDateBetween(userId, start, end);

        return attendanceRecords.stream()
                .map(record -> new GetAttendanceRecDTO(
                        record.getUserId(),
                        record.getDate(),
                        record.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
