package com.example.lms.service;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.dto.student.*;
import com.example.lms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminStudentService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminStudentService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<StudentResponse> listStudents() {
        return userRepository.findByRoleOrderByIdAsc(Role.STUDENT).stream().map(this::map).toList();
    }

    public StudentResponse getStudent(Long id) {
        return map(findStudent(id));
    }

    public StudentResponse create(StudentCreateRequest req) {
        String username = req.getUsername().trim();
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.STUDENT);
        u.setIsApproved(req.getIsApproved() != null ? req.getIsApproved() : false);
        u.setIsActive(req.getIsActive() != null ? req.getIsActive() : true);
        u.setDeactivationDate(req.getDeactivationDate());
        return map(userRepository.save(u));
    }

    public StudentResponse update(Long id, StudentUpdateRequest req) {
        User u = findStudent(id);
        if (req.getUsername() != null) {
            String nu = req.getUsername().trim();
            if (!nu.equalsIgnoreCase(u.getUsername()) && userRepository.existsByUsernameIgnoreCase(nu)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
            u.setUsername(nu);
        }
        if (req.getPassword() != null) {
            u.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        if (req.getIsApproved() != null) {
            u.setIsApproved(req.getIsApproved());
        }
        if (req.getIsActive() != null) {
            u.setIsActive(req.getIsActive());
        }
        if (req.getDeactivationDate() != null) {
            u.setDeactivationDate(req.getDeactivationDate());
        }
        return map(userRepository.save(u));
    }

    public void delete(Long id) {
        userRepository.delete(findStudent(id));
    }

    public void setApproval(Long id, boolean approved) {
        User u = findStudent(id);
        u.setIsApproved(approved);
        userRepository.save(u);
    }

    public void setActive(Long id, boolean active) {
        User u = findStudent(id);
        u.setIsActive(active);
        userRepository.save(u);
    }

    public void setDeactivation(Long id, java.time.LocalDateTime deactivationDate) {
        User u = findStudent(id);
        u.setDeactivationDate(deactivationDate);
        userRepository.save(u);
    }

    private User findStudent(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        if (u.getRole() != Role.STUDENT) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return u;
    }

    private StudentResponse map(User u) {
        StudentResponse r = new StudentResponse();
        r.setId(u.getId());
        r.setUsername(u.getUsername());
        r.setRole(u.getRole());
        r.setIsApproved(u.getIsApproved());
        r.setIsActive(u.getIsActive());
        r.setDeactivationDate(u.getDeactivationDate());
        return r;
    }
}
