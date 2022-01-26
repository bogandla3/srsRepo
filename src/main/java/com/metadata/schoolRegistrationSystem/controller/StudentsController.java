package com.metadata.schoolRegistrationSystem.controller;

import com.metadata.schoolRegistrationSystem.dao.CoursesDao;
import com.metadata.schoolRegistrationSystem.dao.StudentsDao;
import com.metadata.schoolRegistrationSystem.model.Course;
import com.metadata.schoolRegistrationSystem.model.Student;
import com.metadata.schoolRegistrationSystem.SchoolRegistrationSystemApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class StudentsController {

    @Autowired
    private StudentsDao studentsDao;

    @Autowired
    private CoursesDao coursesDao;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAll() {
        try {
            List<Student> students = studentsDao.findAll();

            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") long id) {
        Optional<Student> studentData = studentsDao.findById(id);

        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/students")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        try {
            Student _student = studentsDao.save(
                    new Student(student.getFirstName(), student.getLastName()));
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> update(@PathVariable("id") long id, @RequestBody Student student) {
        Optional<Student> studentData = studentsDao.findById(id);

        if (studentData.isPresent()) {
            Student _student = studentData.get();
            _student.setFirstName(student.getFirstName());
            _student.setLastName(student.getLastName());
            return new ResponseEntity<>(studentsDao.save(_student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        try {
            studentsDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            studentsDao.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/students/{id}/register/{course_id}", method = RequestMethod.POST)
    public ResponseEntity<Student> register(@PathVariable("id") long id, @PathVariable("course_id") long course_id) {
        Optional<Student> studentData = studentsDao.findById(id);
        Optional<Course> courseData = coursesDao.findById(course_id);

        if (studentData.isPresent() && courseData.isPresent()) {
            Student _student = studentData.get();
            Course _course = courseData.get();

            if (_student.getCourses().size() < SchoolRegistrationSystemApplication.COURSES_LIMIT
                    && _course.getStudents().size() < SchoolRegistrationSystemApplication.STUDENTS_LIMIT) {

                _student.getCourses().add(_course);
                return new ResponseEntity<>(studentsDao.save(_student), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}