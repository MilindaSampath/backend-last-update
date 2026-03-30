package com.example.lms.controller;

import com.example.lms.dto.student.*;
import com.example.lms.service.AdminStudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@SecurityRequirement(name = "bearerAuth")
public class AdminStudentController {

    private final AdminStudentService adminStudentService;

    public AdminStudentController(AdminStudentService adminStudentService) {
        this.adminStudentService = adminStudentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> list() {
        return ResponseEntity.ok(adminStudentService.listStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(adminStudentService.getStudent(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminStudentService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(adminStudentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminStudentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/approval")
    public ResponseEntity<Void> approval(@PathVariable Long id, @Valid @RequestBody ApprovalBody body) {
        adminStudentService.setApproval(id, body.getApproved());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> active(@PathVariable Long id, @Valid @RequestBody ActiveBody body) {
        adminStudentService.setActive(id, body.getActive());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivation")
    public ResponseEntity<Void> deactivation(@PathVariable Long id, @Valid @RequestBody DeactivationBody body) {
        adminStudentService.setDeactivation(id, body.getDeactivationDate());
        return ResponseEntity.noContent().build();
    }
}
