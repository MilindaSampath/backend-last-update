package com.example.lms.dto.student;

import jakarta.validation.constraints.NotNull;

public class ActiveBody {
    @NotNull
    private Boolean active;

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
