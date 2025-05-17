package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.faculty.FacultyRequestDTO;
import com.animesh.facecount.dto.faculty.FacultyResponseDTO;
import com.animesh.facecount.services.FacultyService;
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
 * Controller for handling Faculty CRUD operations and queries.
 * Provides endpoints for managing and retrieving faculty information.
 */
@RestController
@RequestMapping("api/faculty")
@RequiredArgsConstructor
@Tag(name = "Faculty Controller", description = "Handles Faculty CRUD operations and faculty queries")
public class FacultyController {

    private final FacultyService facultyService;

    /**
     * Retrieves a list of all faculty members.
     *
     * @return List of all faculty
     */
    @Operation(summary = "Get all faculty", description = "Retrieves all faculty members")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty found"),
        @ApiResponse(responseCode = "404", description = "No faculty found")
    })
    @GetMapping("admin/all")
    public ResponseEntity<List<FacultyResponseDTO>> getAllFaculty() {
        List<FacultyResponseDTO> facultyList = facultyService.getAllFaculty();
        if (!facultyList.isEmpty()) {
            return new ResponseEntity<>(facultyList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of faculty based on department.
     *
     * @param dept Department name
     * @return List of faculty in the specified department
     */
    @Operation(summary = "Get faculty by department", description = "Retrieves faculty belonging to the specified department")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty found"),
        @ApiResponse(responseCode = "404", description = "No faculty found")
    })
    @GetMapping("admin/dept/{dept}")
    public ResponseEntity<List<FacultyResponseDTO>> getFacultyByDepartment(
            @Parameter(description = "Department name") @PathVariable String dept) {
        List<FacultyResponseDTO> facultyList = facultyService.getFacultyByDepartment(dept);
        if (!facultyList.isEmpty()) {
            return new ResponseEntity<>(facultyList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a faculty member by their ID.
     *
     * @param facultyId Faculty ID
     * @return Faculty details if found
     */
    @Operation(summary = "Get faculty by ID", description = "Retrieves faculty details by faculty ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty found"),
        @ApiResponse(responseCode = "404", description = "Faculty not found")
    })
    @GetMapping("admin/details/{facultyId}")
    public ResponseEntity<FacultyResponseDTO> getFacultyById(
            @Parameter(description = "Faculty ID") @PathVariable Long facultyId) {
        FacultyResponseDTO faculty = facultyService.getFacultyById(facultyId);
        if (faculty != null) {
            return new ResponseEntity<>(faculty, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Registers a new faculty member.
     *
     * @param facultyDTO Faculty data transfer object
     * @return Success or error message
     */
    @Operation(summary = "Register new faculty", description = "Registers a new faculty member in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid faculty data")
    })
    @PostMapping("admin/register")
    public ResponseEntity<String> addFaculty(
            @Parameter(description = "Faculty registration data") @RequestBody FacultyRequestDTO facultyDTO) {
        String response = facultyService.addFaculty(facultyDTO);
        if (!response.startsWith("User ")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing faculty member's information.
     *
     * @param facultyDTO Updated faculty data
     * @param facultyId Faculty ID
     * @return Success or error message
     */
    @Operation(summary = "Update faculty", description = "Updates an existing faculty member's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty updated successfully"),
        @ApiResponse(responseCode = "404", description = "Faculty not found")
    })
    @PutMapping("admin/update/{facultyId}")
    public ResponseEntity<String> updateFaculty(
            @Parameter(description = "Updated faculty data") @RequestBody FacultyRequestDTO facultyDTO,
            @Parameter(description = "Faculty ID") @PathVariable Long facultyId) {
        String response = facultyService.updateFaculty(facultyDTO, facultyId);
        if (response.startsWith("Update")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a faculty member from the system by faculty ID.
     *
     * @param facultyId Faculty ID
     * @return Success or error message
     */
    @Operation(summary = "Delete faculty", description = "Deletes a faculty member from the system by faculty ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Faculty not found")
    })
    @DeleteMapping("admin/delete/{facultyId}")
    public ResponseEntity<String> removeFaculty(
            @Parameter(description = "Faculty ID") @PathVariable Long facultyId) {
        if (facultyService.removeFaculty(facultyId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("USER NOT FOUND", HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves the authenticated faculty member's own data.
     *
     * @param authentication Spring Security authentication object
     * @return Faculty details if found
     */
    @Operation(summary = "Get own faculty data", description = "Retrieves the authenticated faculty member's own data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Faculty found"),
        @ApiResponse(responseCode = "404", description = "Faculty not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<FacultyResponseDTO> getOwnFacultyData(Authentication authentication) {
        try {
            Long facultyId = Long.parseLong(authentication.getName());
            FacultyResponseDTO faculty = facultyService.getFacultyById(facultyId);
            if (faculty != null) {
                return new ResponseEntity<>(faculty, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
