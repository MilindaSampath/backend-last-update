package com.example.lms.service;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.dto.auth.AuthResponse;
import com.example.lms.dto.auth.LoginRequest;
import com.example.lms.dto.auth.RegisterRequest;
import com.example.lms.repository.UserRepository;
import com.example.lms.security.JwtService;
import com.example.lms.time.SriLankaTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SriLankaTime sriLankaTime;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            SriLankaTime sriLankaTime
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.sriLankaTime = sriLankaTime;
    }

    public void register(RegisterRequest request) {
        String username = request.getUsername().trim();
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole(Role.STUDENT);
        u.setIsApproved(false);
        u.setIsActive(true);
        userRepository.save(u);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        String key = request.getUsername().trim();
        User user = userRepository.findByUsernameIgnoreCase(key)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is inactive");
        }

        if (user.getRole() == Role.STUDENT) {
            if (Boolean.FALSE.equals(user.getIsApproved())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account pending approval");
            }
            if (user.getDeactivationDate() != null
                    && sriLankaTime.now().isAfter(user.getDeactivationDate())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account deactivated");
            }
        }

        String sessionId = UUID.randomUUID().toString();
        user.setLastLoginSessionId(sessionId);
        userRepository.save(user);

        return new AuthResponse(jwtService.generate(user, sessionId));
    }
}
