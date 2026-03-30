package com.example.lms.bootstrap;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private static final String ADMIN_USER = "milinda273";
    private static final String ADMIN_PASS = "MilindaSampath273@";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.existsByUsernameIgnoreCase(ADMIN_USER)) {
            log.debug("Admin '{}' already exists.", ADMIN_USER);
            return;
        }
        User admin = new User();
        admin.setUsername(ADMIN_USER);
        admin.setPassword(passwordEncoder.encode(ADMIN_PASS));
        admin.setRole(Role.ADMIN);
        admin.setIsApproved(true);
        admin.setIsActive(true);
        admin.setDeactivationDate(null);
        admin.setLastLoginSessionId(null);
        userRepository.save(admin);
        log.info("Created default admin user '{}'.", ADMIN_USER);
    }
}
