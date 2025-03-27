package com.animesh.facecount.services;

import com.animesh.facecount.dto.CourseDTO;
import com.animesh.facecount.entities.Course;
import com.animesh.facecount.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepo;

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public CourseDTO getCourseByCode(String courseCode) {
        Course course = courseRepo.findByCourseCode(courseCode).orElse(new Course());
        if (course.getId() != null) {
            return courseToCourseDTO(course);
        }
        return null;
    }

    public CourseDTO addCourse(CourseDTO courseDTO) {
        Course course = courseRepo.save(courseDTOToCourse(courseDTO));
        return courseToCourseDTO(course);
    }

    private Course courseDTOToCourse(CourseDTO dto) {
        return new Course(
                dto.courseCode(),
                dto.courseName(),
                dto.facultyId()
        );
    }

    private CourseDTO courseToCourseDTO(Course course) {
        return new CourseDTO(
                course.getCourseCode(),
                course.getCourseName(),
                course.getFacultyId()
        );
    }

    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course course = courseRepo.findByCourseCode(courseDTO.courseCode()).orElse(new Course());
        course.setCourseCode(courseDTO.courseCode());
        course.setCourseName(courseDTO.courseName());
        course.setFacultyId(courseDTO.facultyId());
        if (course.getId() != null) {
            return courseToCourseDTO(courseRepo.save(course));
        }
        else return null;
    }

    public boolean exists(String code) {
        Course course = courseRepo.findByCourseCode(code).orElse(new Course());
        return course.getId() != null;
    }
}

/*
//TODO
    Check the updateCourse method for working

*/
