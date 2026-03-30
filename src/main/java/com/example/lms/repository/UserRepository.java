package com.example.lms.repository;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    List<User> findByRoleOrderByIdAsc(Role role);
}
