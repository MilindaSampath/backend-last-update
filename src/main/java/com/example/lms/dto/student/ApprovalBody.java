package com.example.lms.dto.student;

import jakarta.validation.constraints.NotNull;

public class ApprovalBody {
    @NotNull
    private Boolean approved;

    public Boolean getApproved() { return approved; }
    public void setApproved(Boolean approved) { this.approved = approved; }
}
