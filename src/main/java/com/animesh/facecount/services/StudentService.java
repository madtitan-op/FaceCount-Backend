package com.animesh.facecount.services;

import com.animesh.facecount.dto.student.StudentRequestDTO;
import com.animesh.facecount.dto.student.StudentResponseDTO;
import com.animesh.facecount.entities.Student;
import com.animesh.facecount.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing student entity. Provides methods to Create, Read, Update & Delete student.
 *
 * @version 1.0
 * @author Animesh Mahata
 */

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Get Students entity from the database according to the yop.
     *
     * @param yop the Pass Out year of the Student
     * @return list of Students passing out on the given year
     */
    public List<StudentResponseDTO> getStudentsBasedOnPassYear(short yop) {
        List<Student> students = studentRepository.findAllByYop(yop);
        return studentListToStudentResponseDTOList(students);
    }

    /**
     * Get Students entity from the database according to the department.
     *
     * @param dept the Department to which the Student belongs
     * @return list of Students belonging to the given department
     */
    public List<StudentResponseDTO> getStudentsBasedOnDepartment(String dept) {
        List<Student> students = studentRepository.findAllByDepartmentContainingIgnoreCase(dept);
        return studentListToStudentResponseDTOList(students);
    }

    /**
     * Get Students entity from the database according to the yop and department.
     *
     * @param yop the Pass Out year of the Student
     * @param dept the Department to which the Student belongs
     * @return list of Students passing out on the given year and belonging to the given department
     */
    public List<StudentResponseDTO> getStudentsBasedOnPassYearAndDepartment(short yop, String dept) {
        List<Student> students = studentRepository.findAllByYopAndDepartmentContainingIgnoreCase(yop, dept);
        return studentListToStudentResponseDTOList(students);
    }

    /**
     * Finds a Student entity in the database.
     *
     * @param student_id the ID of the Student entity which will be used to find the Student
     * @return details of the Student
     */
    public StudentResponseDTO getStudentByRollNo(Long student_id) {
        Student student = studentRepository.findById(student_id).orElse(null);
        if (student != null) {
            return studentToStudentResponseDTO(student);
        }
        return null;
    }

    /**
     * Add a Student entity to the database.
     *
     * @param studentDTO details of Student that need to be added
     * @return if the details of Student was Added or Not
     */
    public String addStudent(StudentRequestDTO studentDTO) {

        if (!studentRepository.existsById(studentDTO.student_id())) {
            Student student = studentRequestDTOToStudent(studentDTO);
            student.setPassword(encoder.encode(studentDTO.password()));
            student.setRole("STUDENT");
            studentRepository.save(student);
            return "Registration SUCCESSFUL";
        }
        return "Already Registered!!!";
    }

    /**
     * Updates a Student entity in the database.
     *
     * @param studentDTO details of the Student that need to be updated
     * @param student_id the ID of the Student entity which will be used to find and update the Student
     * @return if the update of Student was Successful or Not
     */
    public String updateStudent(StudentRequestDTO studentDTO, Long student_id) {
        Student student = studentRepository.findById(student_id).orElse(null);
        if (student != null) {
            student.setStudent_id(studentDTO.student_id());
            student.setName(studentDTO.name());
            student.setYop(studentDTO.yop());
            student.setDepartment(studentDTO.department());
            student.setEmail(studentDTO.email());
            student.setPassword(encoder.encode(studentDTO.password()));

            studentRepository.save(student);
            return "Updated SUCCESSFULLY!";
        }
        return "STUDENT NOT FOUND!!!";
    }

    /**
     * Removes a Student entity from the database.
     *
     * @param student_id the ID of the Student entity which will be used to find and delete the Student
     * @return if the removal of Student was Successful or Not
     */
    public boolean removeStudent(Long student_id) {
        if (studentRepository.existsById(student_id)) {
            studentRepository.deleteById(student_id);
            return true;
        }
        return false;
    }

    /*PRIVATE METHODS*/
    /**
     * Converts StudentRequestDTO to Student entity.
     *
     * @param studentDTO the StudentRequestDTO
     * @return the Student entity
     */
    private Student studentRequestDTOToStudent(StudentRequestDTO studentDTO) {
        return new Student(
                studentDTO.student_id(),
                studentDTO.email(),
                studentDTO.name(),
                studentDTO.password(),
                studentDTO.role(),
                studentDTO.department(),
                studentDTO.yop()
        );
    }

    /**
     * Converts Student entity to StudentResponseDTO.
     *
     * @param student the Student entity.
     *
     * @return the StudentResponseDTO
     */
    private StudentResponseDTO studentToStudentResponseDTO(Student student) {
        return new StudentResponseDTO(
                student.getStudent_id(),
                student.getName(),
                student.getYop(),
                student.getDepartment(),
                student.getEmail(),
                student.getRole()
        );
    }

    /**
     * Converts a list of Student entity to a list of StudentResponseDTO
     *
     * @param students the list of Student entity
     * @return the list of StudentResponseDTO
     */
    private List<StudentResponseDTO> studentListToStudentResponseDTOList(List<Student> students) {
        return students.stream()
                .map(this::studentToStudentResponseDTO)
                .toList();
    }
}
