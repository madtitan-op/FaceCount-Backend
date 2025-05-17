package com.animesh.facecount.services;

import com.animesh.facecount.dto.attendance.AttendanceRequestDTO;
import com.animesh.facecount.dto.attendance.AttendanceResponseDTO;
import com.animesh.facecount.entities.AttendanceRecord;
import com.animesh.facecount.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing attendance records.
 * Provides methods to mark attendance and fetch attendance records.
 * 
 * @version 1.0
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
    private AttendanceResponseDTO attendanceRecordToGetAttendanceDTO(AttendanceRecord record) {
        return new AttendanceResponseDTO(
                record.getUserid(),
                record.getStatus(),
                record.getDate(),
                record.getTime(),
                record.getMarked_by_faculty_id(),
                record.getMarked_by_system_id()
        );
    }

    /**
     * Converts an AttendanceMarkDTO to an AttendanceRecord entity.
     * 
     * @param markDTO the attendance mark DTO
     * @return the attendance record entity
     */
    private AttendanceRecord attendanceMarkDTOToAttendanceRecord(AttendanceRequestDTO markDTO) {
        AttendanceRecord record = new AttendanceRecord();
        record.setUserid(markDTO.userId());
        record.setStatus(markDTO.status());
        record.setDate(LocalDate.now());
        record.setTime(LocalTime.now());
        if (markDTO.role().equals("SYSTEM")) {
            record.setMarked_by_system_id((int) markDTO.marker_id());
            record.setMarked_by_faculty_id(null);
        }
        else {
            record.setMarked_by_faculty_id(markDTO.marker_id());
            record.setMarked_by_system_id(null);
        }
        return record;
    }

    /**
     * Marks attendance in the database.
     * 
     * @param markDTO the attendance mark DTO
     * @return the attendance record DTO
     */
    public AttendanceResponseDTO markAttendance(AttendanceRequestDTO markDTO) {
        try {
            AttendanceRecord record = attendanceRepo.save(attendanceMarkDTOToAttendanceRecord(markDTO));
            return attendanceRecordToGetAttendanceDTO(record);
        }catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("Either Data is duplicated OR Doesn't meet the criteria");
        }
    }

    /**
     * Fetches the attendance records of a certain student for a specific month.
     * 
     * @param month the month for which to fetch attendance
     * @param year the year for which to fetch attendance
     * @param userId the ID of the student
     * @return the list of attendance record DTOs
     */
    public List<AttendanceResponseDTO> fetchAttendanceByMonth(int month, int year, Long userId) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, getTotalDays(month, year));

        List<AttendanceRecord> attendanceRecords = attendanceRepo.findAttendanceRecordsByUseridAndDateBetween(userId, start, end);

        return attendanceRecords.stream()
                .map(this::attendanceRecordToGetAttendanceDTO)
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
    public List<AttendanceResponseDTO> fetchAttendanceByDay(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);

        List<AttendanceRecord> attendanceRecords = attendanceRepo.findAttendanceRecordsByDate(date);

        return attendanceRecords.stream()
                .filter(record -> record.getStatus().equals("PRESENT"))
                .map(this::attendanceRecordToGetAttendanceDTO)
                .collect(Collectors.toList());
    }
}
