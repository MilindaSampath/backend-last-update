package com.example.lms.dto.student;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DeactivationBody {
    /** Wall-clock Sri Lanka local time; compared with {@link com.example.lms.time.SriLankaTime#now()}. */
    @NotNull
    private LocalDateTime deactivationDate;

    public LocalDateTime getDeactivationDate() { return deactivationDate; }
    public void setDeactivationDate(LocalDateTime deactivationDate) { this.deactivationDate = deactivationDate; }
}
