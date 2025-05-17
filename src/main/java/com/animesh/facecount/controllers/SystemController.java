package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.system.FaceCountSystemDTO;
import com.animesh.facecount.entities.FaceCountSystem;
import com.animesh.facecount.services.FaceCountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling CRUD operations for FaceCountSystem.
 */
@RestController
@RequestMapping("api/system")
@RequiredArgsConstructor
@Tag(name = "System Controller", description = "Handles CRUD operations for FaceCountSystem")
public class SystemController {

    private final FaceCountService service;

    /**
     * Get all systems.
     */
    @Operation(summary = "Get all systems", description = "Retrieves all FaceCountSystem records")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Systems found"),
        @ApiResponse(responseCode = "404", description = "No systems found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<FaceCountSystemDTO>> getAllSystems() {
        List<FaceCountSystemDTO> systems = service.getAllSystems();
        if (systems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(systems, HttpStatus.OK);
    }

    /**
     * Get a system by ID.
     */
    @Operation(summary = "Get system by ID", description = "Retrieves a FaceCountSystem by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "System found"),
        @ApiResponse(responseCode = "404", description = "System not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaceCountSystemDTO> getSystemById(
            @Parameter(description = "System ID") @PathVariable int id) {
        FaceCountSystemDTO system = service.getSystemById(id);
        if (system != null) {
            return new ResponseEntity<>(system, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get systems by department.
     */
    @Operation(summary = "Get systems by department", description = "Retrieves systems by department")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Systems found"),
        @ApiResponse(responseCode = "404", description = "No systems found")
    })
    @GetMapping("/department/{department}")
    public ResponseEntity<List<FaceCountSystemDTO>> getSystemsByDepartment(
            @Parameter(description = "Department name") @PathVariable String department) {
        List<FaceCountSystemDTO> systems = service.getSystemsByDepartment(department);
        if (systems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(systems, HttpStatus.OK);
    }

    /**
     * Create a new system.
     */
    @Operation(summary = "Create system", description = "Creates a new FaceCountSystem")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "System created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<FaceCountSystemDTO> createSystem(
            @Parameter(description = "FaceCountSystem object") @RequestBody FaceCountSystem system) {
        FaceCountSystemDTO saved = service.createSystem(system);
        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Update an existing system.
     */
    @Operation(summary = "Update system", description = "Updates an existing FaceCountSystem")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "System updated"),
        @ApiResponse(responseCode = "404", description = "System not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<FaceCountSystemDTO> updateSystem(
            @Parameter(description = "System ID") @PathVariable int id,
            @Parameter(description = "Updated FaceCountSystem object") @RequestBody FaceCountSystem system) {
        FaceCountSystemDTO updated = service.updateSystem(id, system);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Delete a system by ID.
     */
    @Operation(summary = "Delete system", description = "Deletes a FaceCountSystem by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "System deleted"),
        @ApiResponse(responseCode = "404", description = "System not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSystem(
            @Parameter(description = "System ID") @PathVariable int id) {
        boolean deleted = service.deleteSystem(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
