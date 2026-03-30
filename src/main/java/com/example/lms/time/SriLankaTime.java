package com.example.lms.time;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class SriLankaTime {
    public static final ZoneId ZONE = ZoneId.of("Asia/Colombo");

    public LocalDateTime now() {
        return LocalDateTime.now(ZONE);
    }
}
