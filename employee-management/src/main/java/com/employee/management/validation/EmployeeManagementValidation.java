package com.employee.management.validation;

import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.exception.AccessForbiddenException;
import com.employee.management.exception.InvalidRoleException;
import com.employee.management.exception.UnAuthorizedUserException;
import com.employee.management.model.ValidRoles;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeManagementValidation {

    // Convert roles (ADMIN, USER, MANAGER) to Long value
    public Long convertAndValidateRole(String role) throws AccessForbiddenException, UnAuthorizedUserException {

        validateRoles(role);
        return switch (role.toUpperCase()) {
            case "ADMIN" -> 1L;
            case "USER" -> 2L;
            case "MANAGER" -> 3L;
            default -> throw new UnAuthorizedUserException("Unauthorized - Role not allowed", 401, LocalDateTime.now());
        };
    }


    public boolean validateRoles(String role) throws AccessForbiddenException, UnAuthorizedUserException {

        // Check if role is null or empty
        if (role == null || role.trim().isEmpty()) {
            throw new InvalidRoleException("Role cannot be null or empty", 400, LocalDateTime.now());
        }

        // Check if the role length is valid (between 3 and 50 characters)
        if (role.length() < 3 || role.length() > 50) {
            throw new InvalidRoleException("Role length must be between 3 and 50 characters", 400,LocalDateTime.now());
        }

        // Check if the role is in the list of predefined roles
        if (!ValidRoles.isValidRole(role)) {
            throw new UnAuthorizedUserException("Invalid role. Accepted roles are ADMIN, USER, MANAGER", 400,LocalDateTime.now());
        }

        return false;
    }


}
