package com.example.bai4.service;

import com.example.bai4.model.Course;
import com.example.bai4.model.Enrollment;
import com.example.bai4.model.Student;
import com.example.bai4.repository.CourseRepository;
import com.example.bai4.repository.EnrollmentRepository;
import com.example.bai4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public boolean enroll(String username, Long courseId) {
        Student student = studentRepository.findByUsername(username).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        if (student == null || course == null) return false;
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) return false;

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());
        enrollmentRepository.save(enrollment);
        return true;
    }

    public List<Enrollment> getEnrollmentsByUsername(String username) {
        Student student = studentRepository.findByUsername(username).orElse(null);
        if (student == null) return List.of();
        return enrollmentRepository.findByStudent(student);
    }
}
