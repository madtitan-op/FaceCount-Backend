package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for User entities.
 * Provides methods to perform CRUD operations and custom queries.
 * 
 * @version 1.0
 * @author Animesh Mahata
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByYop(short yop);

    List<User> findAllByRoleContainingIgnoreCase(String role);

    User findByUserid(String userid);

    List<User> findAllByDepartmentContainingIgnoreCaseAndYopAndRole(String department, short yop, String role);

    List<User> findAllByDepartmentContainingIgnoreCaseAndRole(String department, String role);
}
