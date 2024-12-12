package com.employee.management.config;

import com.employee.management.exception.UnAuthorizedUserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

public class RoleBasedAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws UnAuthorizedUserException, ServletException, IOException {

        String roleHeader = request.getHeader("Role");  // Extract role from header

        if (roleHeader != null) {

            // Add validation for role if needed (e.g., check if it's a valid role in your system)
            SimpleGrantedAuthority authority;

            // Check the role and map it to an authority
            switch (roleHeader.toUpperCase()) {
                case "ADMIN":
                    authority = new SimpleGrantedAuthority("ROLE_ADMIN");
                    break;
                case "USER":
                    authority = new SimpleGrantedAuthority("ROLE_USER");
                    break;
                case "MANAGER":
                    authority = new SimpleGrantedAuthority("ROLE_MANAGER");
                    break;
                default:
                    throw new UnAuthorizedUserException("Unauthorized - Role not allowed",401, LocalDateTime.now());
            }

            // Create an authentication token and set it in the SecurityContextHolder
            Authentication authentication = new UsernamePasswordAuthenticationToken("user", null, Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication); // Set authentication in context
        }

        filterChain.doFilter(request, response);
    }
}
