package com.example.lms.dto.student;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class StudentUpdateRequest {
    @Size(min = 3, max = 50)
    private String username;
    @Size(min = 8, max = 255)
    private String password;
    private Boolean isApproved;
    private Boolean isActive;
    private LocalDateTime deactivationDate;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean approved) { this.isApproved = approved; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { this.isActive = active; }
    public LocalDateTime getDeactivationDate() { return deactivationDate; }
    public void setDeactivationDate(LocalDateTime deactivationDate) { this.deactivationDate = deactivationDate; }
}
