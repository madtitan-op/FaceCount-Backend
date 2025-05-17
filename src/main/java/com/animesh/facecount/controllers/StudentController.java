package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.student.StudentRequestDTO;
import com.animesh.facecount.dto.student.StudentResponseDTO;
import com.animesh.facecount.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Controller for handling Student CRUD operations and queries.
 * Provides endpoints for managing and retrieving student information.
 */
@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
@Tag(name = "Student Controller", description = "Handles Student CRUD operations and student queries")
public class StudentController{

    private final StudentService studentService;

    /**
     * Retrieves a list of students based on their year of passing.
     *
     * @param yop Year of passing
     * @return List of students matching the year of passing
     */
    @Operation(summary = "Get students by year of passing", description = "Retrieves students who passed in the specified year")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Students found"),
        @ApiResponse(responseCode = "404", description = "No students found")
    })
    @GetMapping("admin/yop/{yop}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsBasedOnPassYear(
            @Parameter(description = "Year of passing") @PathVariable short yop) {
        List<StudentResponseDTO> students = studentService.getStudentsBasedOnPassYear(yop);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of students based on their department.
     *
     * @param dept Department name
     * @return List of students in the specified department
     */
    @Operation(summary = "Get students by department", description = "Retrieves students belonging to the specified department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Students found"),
        @ApiResponse(responseCode = "404", description = "No students found")
    })
    @GetMapping("admin/dept/{dept}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsBasedOnDepartment(
            @Parameter(description = "Department name") @PathVariable String dept) {
        List<StudentResponseDTO> students = studentService.getStudentsBasedOnDepartment(dept);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of students based on year of passing and department.
     *
     * @param yop Year of passing
     * @param dept Department name
     * @return List of students matching the criteria
     */
    @Operation(summary = "Get students by year of passing and department", description = "Retrieves students by year of passing and department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Students found"),
        @ApiResponse(responseCode = "404", description = "No students found")
    })
    @GetMapping("admin/{yop}/{dept}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsBasedOnPassYearAndDepartment(
            @Parameter(description = "Year of passing") @PathVariable short yop,
            @Parameter(description = "Department name") @PathVariable String dept
    ) {
        List<StudentResponseDTO> students = studentService.getStudentsBasedOnPassYearAndDepartment(yop, dept);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a student by their roll number.
     *
     * @param rollNo Student roll number
     * @return Student details if found
     */
    @Operation(summary = "Get student by roll number", description = "Retrieves student details by roll number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("admin/details/{rollNo}")
    public ResponseEntity<StudentResponseDTO> getStudentByRollNo(
            @Parameter(description = "Student roll number") @PathVariable Long rollNo) {
        StudentResponseDTO student = studentService.getStudentByRollNo(rollNo);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Registers a new student.
     *
     * @param studentDTO Student data transfer object
     * @return Success or error message
     */
    @Operation(summary = "Register new student", description = "Registers a new student in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid student data")
    })
    @PostMapping("admin/register")
    public ResponseEntity<String> addUser(
            @Parameter(description = "Student registration data") @RequestBody StudentRequestDTO studentDTO) {
        String response = studentService.addStudent(studentDTO);
        if (!response.startsWith("User ")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing student's information.
     *
     * @param studentDTO Updated student data
     * @param student_id Student ID
     * @return Success or error message
     */
    @Operation(summary = "Update student", description = "Updates an existing student's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PutMapping("admin/update/{student_id}")
    public ResponseEntity<String> updateUser(
            @Parameter(description = "Updated student data") @RequestBody StudentRequestDTO studentDTO,
            @Parameter(description = "Student ID") @PathVariable Long student_id) {
        String response = studentService.updateStudent(studentDTO, student_id);
        if (response.startsWith("Update")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a student from the system by student ID.
     *
     * @param student_id Student ID
     * @return Success or error message
     */
    @Operation(summary = "Delete student", description = "Deletes a student from the system by student ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("admin/delete/{student_id}")
    public ResponseEntity<String> removeUser(
            @Parameter(description = "Student ID") @PathVariable Long student_id) {
        if (studentService.removeStudent(student_id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves the authenticated student's own data.
     *
     * @param authentication Spring Security authentication object
     * @return Student details if found
     */
    @Operation(summary = "Get own student data", description = "Retrieves the authenticated student's own data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<StudentResponseDTO> getOwnStudentData(Authentication authentication) {
        try {
            Long rollNo = Long.parseLong(authentication.getName());
            StudentResponseDTO student = studentService.getStudentByRollNo(rollNo);
            if (student != null) {
                return new ResponseEntity<>(student, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
