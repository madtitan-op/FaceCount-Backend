package com.animesh.facecount.repositories;

import com.animesh.facecount.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByYop(short yop);

    List<User> findAllByRoleContainingIgnoreCase(String role);
/*
    List<User> findAllByDepartmentContainingIgnoreCase(String department);

    List<User> findAllByDepartmentContainingIgnoreCaseAndYop(String department, short yop);*/

    User findByUserid(String userid);

    List<User> findAllByDepartmentContainingIgnoreCaseAndYopAndRole(String department, short yop, String role);

    List<User> findAllByDepartmentContainingIgnoreCaseAndRole(String department, String role);

//    User updateById(Integer id, User user);
}
