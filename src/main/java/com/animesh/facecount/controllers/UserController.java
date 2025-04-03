package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.user.UserRequestDTO;
import com.animesh.facecount.dto.user.UserResponseDTO;
import com.animesh.facecount.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        if (!allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>() ,HttpStatus.NOT_FOUND);
    }

    @GetMapping("{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersBasedOnRole(@PathVariable String role) {
        List<UserResponseDTO> users = userService.getUsersBasedOnRole(role.toUpperCase());
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("student/yop/{yop}")
    public ResponseEntity<List<UserResponseDTO>> getStudentsBasedOnPassYear(@PathVariable short yop) {
        List<UserResponseDTO> users = userService.getStudentsBasedOnPassYear(yop);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("student/dept/{dept}")
    public ResponseEntity<List<UserResponseDTO>> getStudentsBasedOnDepartment(@PathVariable String dept) {
        List<UserResponseDTO> users = userService.getStudentsBasedOnDepartment(dept);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("student/{yop}/{dept}")
    public ResponseEntity<List<UserResponseDTO>> getStudentsBasedOnPassYearAndDepartment(
            @PathVariable short yop,
            @PathVariable String dept
    ) {
        List<UserResponseDTO> students = userService.getStudentsBasedOnPassYearAndDepartment(yop, dept);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("student/details/{rollNo}")
    public ResponseEntity<UserResponseDTO> getStudentByRollNo(@PathVariable String rollNo) {
        UserResponseDTO student = userService.getStudentByRollNo(rollNo);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("register")
    public ResponseEntity<String> addUser(@RequestBody UserRequestDTO userDTO) {
        String response = userService.addUser(userDTO);
        if (!response.startsWith("User ")) {
            return new ResponseEntity<>(response, HttpStatus.OK);   // Responding with class address
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("update/{userid}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO userDTO, @PathVariable String userid) {
        String response = userService.updateUser(userDTO, userid);
        if (response.startsWith("Update")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete/{userid}")
    public ResponseEntity<String> removeUser(@PathVariable String userid) {
        if(userService.removeUser(userid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
    }

}
