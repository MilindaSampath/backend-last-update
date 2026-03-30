package com.example.lms.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Interpreted as wall-clock in Asia/Colombo for comparisons. */
    @Column(name = "deactivation_date")
    private LocalDateTime deactivationDate;

    @Column(name = "last_login_session_id", length = 64)
    private String lastLoginSessionId;

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean approved) { this.isApproved = approved; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { this.isActive = active; }
    public LocalDateTime getDeactivationDate() { return deactivationDate; }
    public void setDeactivationDate(LocalDateTime deactivationDate) { this.deactivationDate = deactivationDate; }
    public String getLastLoginSessionId() { return lastLoginSessionId; }
    public void setLastLoginSessionId(String lastLoginSessionId) { this.lastLoginSessionId = lastLoginSessionId; }
}
