package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for AttendanceRecord entities.
 * Provides methods to perform CRUD operations and custom queries.
 * 
 * @version 1.0
 */
@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {
    List<AttendanceRecord> findAttendanceRecordsByUseridAndDateBetween(Long userId, LocalDate start, LocalDate end);

    List<AttendanceRecord> findAttendanceRecordsByDate(LocalDate date);
}
