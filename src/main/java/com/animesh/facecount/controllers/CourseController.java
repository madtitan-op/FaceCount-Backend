package com.animesh.facecount.controllers;

import com.animesh.facecount.dto.CourseDTO;
import com.animesh.facecount.entities.Course;
import com.animesh.facecount.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/course/{courseCode}")
    public CourseDTO getCourse(@PathVariable String courseCode) {
        return courseService.getCourseByCode(courseCode);
    }

    @PostMapping("/course")
    public CourseDTO addCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.addCourse(courseDTO);
    }

    @PutMapping("/course/{code}")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable String code) {
        if (courseService.exists(code)) {
            CourseDTO dto = courseService.updateCourse(courseDTO);
            if (dto != null) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
