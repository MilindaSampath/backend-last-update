package com.example.lms.dto.student;

import com.example.lms.domain.Role;

import java.time.LocalDateTime;

public class StudentResponse {
    private Long id;
    private String username;
    private Role role;
    private Boolean isApproved;
    private Boolean isActive;
    private LocalDateTime deactivationDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean approved) { this.isApproved = approved; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { this.isActive = active; }
    public LocalDateTime getDeactivationDate() { return deactivationDate; }
    public void setDeactivationDate(LocalDateTime deactivationDate) { this.deactivationDate = deactivationDate; }
}
