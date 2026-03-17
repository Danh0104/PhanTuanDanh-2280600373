package com.example.bai4.repository;

import com.example.bai4.model.Enrollment;
import com.example.bai4.model.Student;
import com.example.bai4.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);
    boolean existsByStudentAndCourse(Student student, Course course);
}
