package com.example.bai4.controller;

import com.example.bai4.model.Enrollment;
import com.example.bai4.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/enroll/{courseId}")
    public String enroll(@PathVariable Long courseId, Principal principal) {
        enrollmentService.enroll(principal.getName(), courseId);
        return "redirect:/home";
    }

    @GetMapping("/enroll/my-courses")
    public String myCourses(Principal principal, Model model) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByUsername(principal.getName());
        model.addAttribute("enrollments", enrollments);
        return "my-courses";
    }
}
