package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByDepartmentContainingIgnoreCase(String department);
}
