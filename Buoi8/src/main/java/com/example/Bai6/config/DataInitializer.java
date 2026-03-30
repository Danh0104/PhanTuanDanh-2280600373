package com.example.Bai6.config;

import com.example.Bai6.model.Account;
import com.example.Bai6.model.Category;
import com.example.Bai6.model.Role;
import com.example.Bai6.repository.AccountRepository;
import com.example.Bai6.repository.CategoryRepository;
import com.example.Bai6.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // ── Roles ──────────────────────────────────────────────────────
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        // ── Tài khoản ─────────────────────────────────────────────────
        if (accountRepository.findByLoginName("admin").isEmpty()) {
            Account admin = new Account();
            admin.setLoginName("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(List.of(adminRole));
            accountRepository.save(admin);
            System.out.println(">>> Tạo tài khoản: admin / 123456 (ADMIN)");
        }

        if (accountRepository.findByLoginName("user").isEmpty()) {
            Account user = new Account();
            user.setLoginName("user");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(List.of(userRole));
            accountRepository.save(user);
            System.out.println(">>> Tạo tài khoản: user / 123456 (USER)");
        }

        // ── Danh mục quần áo ─────────────────────────────────────────
        List<String> categories = List.of(
                "Áo thun & áo phông",
                "Áo sơ mi",
                "Áo khoác & áo hoodie",
                "Quần jeans",
                "Quần short & quần thể thao",
                "Váy & đầm",
                "Phụ kiện thời trang"
        );

        for (String name : categories) {
            categoryRepository.findByName(name)
                    .orElseGet(() -> {
                        Category cat = new Category();
                        cat.setName(name);
                        categoryRepository.save(cat);
                        System.out.println(">>> Tạo danh mục: " + name);
                        return cat;
                    });
        }
    }
}
