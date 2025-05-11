package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Course entities.
 * Provides methods to perform CRUD operations and custom queries.
 * 
 * @version 1.0
 * @author Animesh Mahata
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseCode(String courseCode);
}
