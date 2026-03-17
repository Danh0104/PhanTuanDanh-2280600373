package com.example.bai4.controller;

import com.example.bai4.model.Course;
import com.example.bai4.service.CategoryService;
import com.example.bai4.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/courses")
    public String listCourses(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Course> coursePage = courseService.findAll(page, 10);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        return "admin/courses";
    }

    @GetMapping("/courses/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/course-form";
    }

    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute Course course,
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        courseService.save(course, imageFile);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        if (course == null) return "redirect:/admin/courses";
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/course-form";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return "redirect:/admin/courses";
    }
}
