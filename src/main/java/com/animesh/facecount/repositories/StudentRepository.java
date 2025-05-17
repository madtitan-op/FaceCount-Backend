package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByYop(short yop);

    List<Student> findAllByDepartmentContainingIgnoreCase(String department);

    List<Student> findAllByYopAndDepartmentContainingIgnoreCase(short yop, String department);
}
