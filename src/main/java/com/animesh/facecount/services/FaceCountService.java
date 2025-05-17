package com.animesh.facecount.services;

import com.animesh.facecount.dto.system.FaceCountSystemDTO;
import com.animesh.facecount.entities.FaceCountSystem;
import com.animesh.facecount.repositories.SystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing FaceCount system operations.
 * Provides methods for CRUD operations and data transformations.
 */
@Service
@RequiredArgsConstructor
public class FaceCountService {

    private final SystemRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Retrieves all face count systems.
     *
     * @return List of FaceCountSystemDTO containing all systems
     */
    public List<FaceCountSystemDTO> getAllSystems() {
        List<FaceCountSystem> systems = repository.findAll();
        return toDTOList(systems);
    }

    /**
     * Retrieves a face count system by its ID.
     *
     * @param id the system ID
     * @return FaceCountSystemDTO if found, null otherwise
     */
    public FaceCountSystemDTO getSystemById(int id) {
        return toDTO(repository.findById(id).orElse(null));
    }

    /**
     * Retrieves all face count systems in a specific department.
     *
     * @param department the department name
     * @return List of FaceCountSystemDTO for the specified department
     */
    public List<FaceCountSystemDTO> getSystemsByDepartment(String department) {
        List<FaceCountSystem> systems = repository.findByDepartmentIgnoreCase(department);
        return toDTOList(systems);
    }

    /**
     * Creates a new face count system.
     *
     * @param system the FaceCountSystem entity to create
     * @return FaceCountSystemDTO of created system, null if system ID already exists
     */
    public FaceCountSystemDTO createSystem(FaceCountSystem system) {
        if (repository.existsById(system.getSystem_id())) {
            return null;
        }
        system.setPassword(encoder.encode(system.getPassword()));
        system.setRole("SYSTEM");
        FaceCountSystem saved = repository.save(system);
        return toDTO(saved);
    }

    /**
     * Updates an existing face count system.
     *
     * @param id the system ID to update
     * @param system the updated FaceCountSystem entity
     * @return FaceCountSystemDTO of updated system, null if system not found
     */
    public FaceCountSystemDTO updateSystem(int id, FaceCountSystem system) {
        FaceCountSystem dbSystem = repository.findById(id).orElse(null);
        if (dbSystem != null) {
            dbSystem.setDepartment(system.getDepartment());
            dbSystem.setPassword(encoder.encode(system.getPassword()));
            repository.save(dbSystem);
            return toDTO(dbSystem);
        }
        return null;
    }

    /**
     * Deletes a face count system by its ID.
     *
     * @param id the system ID to delete
     * @return true if deleted successfully, false if system not found
     */
    public boolean deleteSystem(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /*PRIVATE METHODS*/

    /**
     * Converts a FaceCountSystem entity to FaceCountSystemDTO.
     *
     * @param system the FaceCountSystem entity to convert
     * @return FaceCountSystemDTO representation of the system, null if input is null
     */
    private FaceCountSystemDTO toDTO(FaceCountSystem system) {
            if (system == null) return null;
            return new FaceCountSystemDTO(
                    system.getSystem_id(),
                    system.getDepartment()
            );
    }

    /**
     * Converts a list of FaceCountSystem entities to DTOs.
     *
     * @param systems list of FaceCountSystem entities to convert
     * @return List of FaceCountSystemDTO, null if input is null
     */
    private List<FaceCountSystemDTO> toDTOList(List<FaceCountSystem> systems) {
        return systems == null ? null : systems.stream().map(this::toDTO).toList();
    }
}
