package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {
    List<AttendanceRecord> findAttendanceRecordsByDateBetween(LocalDate dateAfter, LocalDate dateBefore);

    List<AttendanceRecord> findAttendanceRecordsByUserIdAndDateBetween(String userId, LocalDate start, LocalDate end);
}
