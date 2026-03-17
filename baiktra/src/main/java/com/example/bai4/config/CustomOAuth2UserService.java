package com.example.bai4.config;

import com.example.bai4.model.Role;
import com.example.bai4.model.Student;
import com.example.bai4.repository.RoleRepository;
import com.example.bai4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<Student> existingStudent = studentRepository.findByEmail(email);
        Student student;
        if (existingStudent.isEmpty()) {
            student = new Student();
            student.setUsername(email);
            student.setEmail(email);
            student.setPassword("");
            Role studentRole = roleRepository.findByName("STUDENT")
                    .orElseThrow(() -> new RuntimeException("Role STUDENT not found"));
            Set<Role> roles = new HashSet<>();
            roles.add(studentRole);
            student.setRoles(roles);
            studentRepository.save(student);
        } else {
            student = existingStudent.get();
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        student.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }
}
