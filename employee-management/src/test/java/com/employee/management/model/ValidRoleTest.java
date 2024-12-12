package com.employee.management.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ValidRoleTest {

    @Test
    void testIsValidRole_ValidRole_Admin() {
        // Act
        boolean result = ValidRoles.isValidRole("ADMIN");

        // Assert
        assertTrue(result, "ADMIN should be a valid role");
    }

    @Test
    void testIsValidRole_ValidRole_User() {
        // Act
        boolean result = ValidRoles.isValidRole("USER");

        // Assert
        assertTrue(result, "USER should be a valid role");
    }

    @Test
    void testIsValidRole_ValidRole_Manager() {
        // Act
        boolean result = ValidRoles.isValidRole("MANAGER");

        // Assert
        assertTrue(result, "MANAGER should be a valid role");
    }

    @Test
    void testIsValidRole_InvalidRole() {
        // Act
        boolean result = ValidRoles.isValidRole("INVALID_ROLE");

        // Assert
        assertFalse(result, "INVALID_ROLE should not be a valid role");
    }

}
