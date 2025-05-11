package com.animesh.facecount.services;

import com.animesh.facecount.dto.attendance.AttendanceMarkDTO;
import com.animesh.facecount.dto.attendance.GetAttendanceRecDTO;
import com.animesh.facecount.entities.AttendanceRecord;
import com.animesh.facecount.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing attendance records.
 * Provides methods to mark attendance and fetch attendance records.
 * 
 * @version 1.0
 * author Animesh Mahata
 */
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;

    /**
     * Converts an AttendanceRecord entity to a GetAttendanceRecDTO.
     * 
     * @param record the attendance record entity
     * @return the attendance record DTO
     */
    public GetAttendanceRecDTO attendanceRecordToGetAttendanceDTO(AttendanceRecord record) {
        return new GetAttendanceRecDTO(
                record.getUserId(),
                /*record.getCourseId(),*/
                record.getDate(),
                record.getStatus()
        );
    }

    /**
     * Converts an AttendanceMarkDTO to an AttendanceRecord entity.
     * 
     * @param markDTO the attendance mark DTO
     * @return the attendance record entity
     */
    public AttendanceRecord attendanceMarkDTOToAttendanceRecord(AttendanceMarkDTO markDTO) {
        AttendanceRecord record = new AttendanceRecord();
        record.setUserId(markDTO.userId());
        // record.setCourseId(markDTO.courseId());
        record.setDate(LocalDate.now());
        record.setStatus(markDTO.status());
        record.setTimestamp(LocalDateTime.now());
        return record;
    }

    /**
     * Marks attendance in the database.
     * 
     * @param markDTO the attendance mark DTO
     * @return the attendance record DTO
     */
    public GetAttendanceRecDTO markAttendance(AttendanceMarkDTO markDTO) {
        AttendanceRecord record = attendanceRepo.save(attendanceMarkDTOToAttendanceRecord(markDTO));
        return attendanceRecordToGetAttendanceDTO(record);
    }

    /**
     * Fetches the attendance records of a certain student for a specific month.
     * 
     * @param month the month for which to fetch attendance
     * @param year the year for which to fetch attendance
     * @param userId the ID of the student
     * @return the list of attendance record DTOs
     */
    public List<GetAttendanceRecDTO> fetchAttendanceByMonth(int month, int year, String userId) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, getTotalDays(month, year));

        List<AttendanceRecord> attendanceRecords = attendanceRepo.findAttendanceRecordsByUserIdAndDateBetween(userId, start, end);

        return attendanceRecords.stream()
                .map(record -> new GetAttendanceRecDTO(
                        record.getUserId(),
                        record.getDate(),
                        record.getStatus()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Fetches the total number of days in a month
     *
     * @param month the month for which to get total number of days
     * @param year the year to which the above month belongs
     * @return the number of days in a month
     */
    private int getTotalDays(int month, int year){
        if(month < 1 || month > 12) {
            throw new RuntimeException("INVALID MONTH: " + month);
        }

        if (month == 2) {
            if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
                return 29;
            }
            return 28;
        }

        Set<Integer> months31 = new HashSet<>(List.of(1, 3, 5, 7, 8, 10, 12));

        if (months31.contains(month)) {
            return 31;
        }

        return 30;
    }

    /**
     * Fetches the attendance records of all students present on a specific date.
     *
     * @param day the day for which to fetch attendance
     * @param month the month for which to fetch attendance
     * @param year the year for which to fetch attendance
     * @return the list of attendance record DTOs
     */
    public List<GetAttendanceRecDTO> fetchAttendanceByDay(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);

        List<AttendanceRecord> attendanceRecords = attendanceRepo.findAttendanceRecordsByDate(date);

        return attendanceRecords.stream()
                .filter(record -> record.getStatus().equals("PRESENT"))
                .map(record -> new GetAttendanceRecDTO(
                        record.getUserId(),
                        record.getDate(),
                        record.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
