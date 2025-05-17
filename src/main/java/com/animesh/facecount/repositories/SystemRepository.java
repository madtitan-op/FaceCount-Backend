package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.FaceCountSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemRepository extends JpaRepository<FaceCountSystem, Integer> {
    List<FaceCountSystem> findByDepartmentIgnoreCase(String department);
}
