package com.animesh.facecount.services;

import com.animesh.facecount.dto.faculty.FacultyRequestDTO;
import com.animesh.facecount.dto.faculty.FacultyResponseDTO;
import com.animesh.facecount.entities.Faculty;
import com.animesh.facecount.repositories.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing faculty entities.
 * Provides methods to Create, Read, Update & Delete faculty.
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Retrieves all faculty members from the database.
     *
     * @return list of all faculty as FacultyResponseDTO
     */
    public List<FacultyResponseDTO> getAllFaculty() {
        List<Faculty> faculties = facultyRepository.findAll();
        return facultyListToFacultyResponseDTOList(faculties);
    }

    /**
     * Retrieves faculty members by department.
     *
     * @param dept the department name
     * @return list of faculty in the given department as FacultyResponseDTO
     */
    public List<FacultyResponseDTO> getFacultyByDepartment(String dept) {
        List<Faculty> faculties = facultyRepository.findAllByDepartmentContainingIgnoreCase(dept);
        return facultyListToFacultyResponseDTOList(faculties);
    }

    /**
     * Retrieves a faculty member by their ID.
     *
     * @param facultyId the faculty ID
     * @return FacultyResponseDTO if found, otherwise null
     */
    public FacultyResponseDTO getFacultyById(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null){
            return facultyToFacultyResponseDTO(faculty);
        }
        else return null;
    }

    /**
     * Registers a new faculty member.
     *
     * @param facultyDTO FacultyRequestDTO containing faculty data
     * @return success or error message
     */
    public String addFaculty(FacultyRequestDTO facultyDTO) {
        Faculty faculty = facultyRequestDTOToFaculty(facultyDTO);
        if (!facultyRepository.existsById(facultyDTO.faculty_id())) {
            faculty.setPassword(encoder.encode(faculty.getPassword()));
            facultyRepository.save(faculty);
            return "Registration SUCCESSFUL!";
        }
        return "Already REGISTERED!!!";
    }

    /**
     * Updates an existing faculty member's information.
     *
     * @param facultyDTO FacultyRequestDTO with updated data
     * @param facultyId the faculty ID
     * @return success or error message
     */
    public String updateFaculty(FacultyRequestDTO facultyDTO, Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null) {
            faculty.setEmail(facultyDTO.email());
            faculty.setName(facultyDTO.name());
            faculty.setPassword(encoder.encode(facultyDTO.password()));
            faculty.setRole(facultyDTO.role());
            faculty.setDepartment(facultyDTO.department());
            facultyRepository.save(faculty);
            return "Update SUCCESSFUL!";
        }
        return "FACULTY NOT FOUND";
    }

    /**
     * Removes a faculty member by ID.
     *
     * @param facultyId the faculty ID
     * @return true if removed, false if not found
     */
    public boolean removeFaculty(Long facultyId) {
        if (facultyRepository.existsById(facultyId)) {
            facultyRepository.deleteById(facultyId);
            return true;
        }
        return false;
    }

    /*PRIVATE METHODS*/

    /**
     * Converts a Faculty entity to FacultyResponseDTO.
     *
     * @param faculty the Faculty entity
     * @return FacultyResponseDTO
     */
    private FacultyResponseDTO facultyToFacultyResponseDTO(Faculty faculty) {
        return new FacultyResponseDTO(
                faculty.getFaculty_id(),
                faculty.getEmail(),
                faculty.getName(),
                faculty.getRole(),
                faculty.getDepartment()
        );
    }

    /**
     * Converts a FacultyRequestDTO to Faculty entity.
     *
     * @param facultyDTO the FacultyRequestDTO
     * @return Faculty entity
     */
    private Faculty facultyRequestDTOToFaculty(FacultyRequestDTO facultyDTO) {
        return new Faculty(
                facultyDTO.faculty_id(),
                facultyDTO.email(),
                facultyDTO.name(),
                facultyDTO.password(),
                facultyDTO.role(),
                facultyDTO.department()
        );
    }

    /**
     * Converts a list of Faculty entities to a list of FacultyResponseDTOs.
     *
     * @param faculties the list of Faculty entities
     * @return list of FacultyResponseDTOs
     */
    private List<FacultyResponseDTO> facultyListToFacultyResponseDTOList(List<Faculty> faculties) {
        return faculties.stream()
                .map(this::facultyToFacultyResponseDTO)
                .toList();
    }
}

