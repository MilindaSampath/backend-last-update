package com.example.lms.security;

import com.example.lms.domain.Role;
import com.example.lms.domain.User;
import com.example.lms.repository.UserRepository;
import com.example.lms.time.SriLankaTime;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SriLankaTime sriLankaTime;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository, SriLankaTime sriLankaTime) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.sriLankaTime = sriLankaTime;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        try {
            Claims claims = jwtService.parse(token);
            String userIdStr = claims.getSubject();
            String tokenSid = claims.get("sid", String.class);
            if (userIdStr == null || tokenSid == null || tokenSid.isBlank()) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            Long userId = Long.parseLong(userIdStr);
            Optional<User> opt = userRepository.findById(userId);
            if (opt.isEmpty()) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            User user = opt.get();
            if (Boolean.FALSE.equals(user.getIsActive())) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            String dbSid = user.getLastLoginSessionId();
            if (dbSid == null || !dbSid.equals(tokenSid)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            if (user.getRole() == Role.STUDENT) {
                if (Boolean.FALSE.equals(user.getIsApproved())) {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(request, response);
                    return;
                }
                if (user.getDeactivationDate() != null
                        && sriLankaTime.now().isAfter(user.getDeactivationDate())) {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(request, response);
                    return;
                }
            }
            String roleName = claims.get("role", String.class);
            if (roleName == null) {
                roleName = user.getRole() != null ? user.getRole().name() : "STUDENT";
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userIdStr,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName))
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
