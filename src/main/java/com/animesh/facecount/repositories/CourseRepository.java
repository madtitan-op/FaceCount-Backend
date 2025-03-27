package com.animesh.facecount.repositories;

import com.animesh.facecount.dto.CourseDTO;
import com.animesh.facecount.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseCode(String courseCode);
}
