package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.user.UserRequestDTO;
import com.animesh.facecount.dto.user.UserResponseDTO;
import com.animesh.facecount.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller for handling user-related operations.
 * Provides endpoints for user management including CRUD operations and specialized queries.
 * @author Animesh Mahata
 * @version 1.0
 * @since 17-03-2025
 */
@RestController()
@RequestMapping("api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Handles user CRUD operations and student queries")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users from the system.
     * @return ResponseEntity containing list of all users if found, empty list otherwise
     */
    @Operation(summary = "Get all users", description = "Fetches all users from the system.")
    @GetMapping("admin/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = userService.getAllUsers();
        if (!allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves users based on their role.
     * @param role The role to filter users by (will be converted to uppercase)
     * @return ResponseEntity containing list of users with specified role
     */
    @Operation(summary = "Get users by role", description = "Fetches users based on their role.")
    @GetMapping("admin/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersBasedOnRole(@PathVariable String role) {
        List<UserResponseDTO> users = userService.getUsersBasedOnRole(role.toUpperCase());
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves students based on their year of passing.
     * @param yop Year of passing to filter students
     * @return ResponseEntity containing list of students from specified year
     */
    @Operation(summary = "Get students by year of passing", description = "Fetches students who passed in a specific year.")
    @GetMapping("admin/student/yop/{yop}")
    public ResponseEntity<List<UserResponseDTO>> getStudentsBasedOnPassYear(@PathVariable short yop) {
        List<UserResponseDTO> users = userService.getStudentsBasedOnPassYear(yop);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves students based on their department.
     * @param dept Department name to filter students
     * @return ResponseEntity containing list of students from specified department
     */
    @Operation(summary = "Get students by department", description = "Fetches students from a specific department.")
    @GetMapping("admin/student/dept/{dept}")
    public ResponseEntity<List<UserResponseDTO>> getStudentsBasedOnDepartment(@PathVariable String dept) {
        List<UserResponseDTO> users = userService.getStudentsBasedOnDepartment(dept);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves students based on both year of passing and department.
     * @param yop Year of passing to filter students
     * @param dept Department name to filter students
     * @return ResponseEntity containing list of filtered students
     */
    @Operation(summary = "Get students by year of passing and department", description = "Fetches students filtered by both year and department.")
    @GetMapping("admin/student/{yop}/{dept}")
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

    /**
     * Retrieves a specific student by their roll number.
     * @param rollNo Roll number of the student to retrieve
     * @return ResponseEntity containing student details if found
     */
    @Operation(summary = "Get student by roll number", description = "Fetches a student's details using their roll number.")
    @GetMapping("student/details/{rollNo}")
    public ResponseEntity<UserResponseDTO> getStudentByRollNo(@PathVariable String rollNo) {
        UserResponseDTO student = userService.getStudentByRollNo(rollNo);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Registers a new user in the system.
     * @param userDTO User details for registration
     * @return ResponseEntity containing response message
     */
    @Operation(summary = "Register new user", description = "Registers a new user in the system.")
    @PostMapping("admin/register")
    public ResponseEntity<String> addUser(@RequestBody UserRequestDTO userDTO) {
        String response = userService.addUser(userDTO);
        if (!response.startsWith("User ")) {
            return new ResponseEntity<>(response, HttpStatus.OK);   // Responding with class address
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing user's information.
     * @param userDTO Updated user details
     * @param userid ID of the user to update
     * @return ResponseEntity containing response message
     */
    @Operation(summary = "Update user", description = "Updates details of an existing user.")
    @PutMapping("admin/update/{userid}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO userDTO, @PathVariable String userid) {
        String response = userService.updateUser(userDTO, userid);
        if (response.startsWith("Update")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Removes a user from the system.
     * @param userid ID of the user to remove
     * @return ResponseEntity with status indicating success or failure
     */
    @Operation(summary = "Delete user", description = "Deletes a user from the system by user ID.")
    @DeleteMapping("admin/delete/{userid}")
    public ResponseEntity<String> removeUser(@PathVariable String userid) {
        if (userService.removeUser(userid)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
    }

}
