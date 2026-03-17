package com.example.bai4.controller;

import com.example.bai4.model.Course;
import com.example.bai4.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseService courseService;

    @GetMapping({"/", "/home", "/courses"})
    public String home(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Course> coursePage = courseService.findAll(page, 5);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<Course> coursePage = courseService.searchByName(keyword, page, 5);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "home";
    }
}
