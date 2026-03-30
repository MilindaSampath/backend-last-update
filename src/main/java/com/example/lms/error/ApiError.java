package com.example.lms.error;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class ApiError {
    private int status;
    private String message;
    private String path;
    private OffsetDateTime timestamp = OffsetDateTime.now(ZoneId.of("Asia/Colombo"));

    public ApiError() {}
    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public OffsetDateTime getTimestamp() { return timestamp; }
}
