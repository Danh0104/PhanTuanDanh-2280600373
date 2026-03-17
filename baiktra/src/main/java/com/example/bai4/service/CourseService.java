package com.example.bai4.service;

import com.example.bai4.model.Course;
import com.example.bai4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Page<Course> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable);
    }

    public Page<Course> searchByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void save(Course course, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads");
            Files.createDirectories(uploadPath);
            Files.copy(imageFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            course.setImage("/uploads/" + fileName);
        }
        courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}
