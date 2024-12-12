package com.employee.management.validation;

import com.employee.management.exception.InvalidRoleException;
import com.employee.management.exception.UnAuthorizedUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeManagementValidationTest {
    @InjectMocks
    private EmployeeManagementValidation validation;

    @BeforeEach
    void setUp() {
        // Any setup needed for the tests
    }

    @Test
    void testConvertAndValidateRole_ValidRole_Admin() throws Exception {
        // Act
        Long result = validation.convertAndValidateRole("ADMIN");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);
    }

    @Test
    void testConvertAndValidateRole_ValidRole_User() throws Exception {
        // Act
        Long result = validation.convertAndValidateRole("USER");

        // Assert
        assertNotNull(result);
        assertEquals(2L, result);
    }

    @Test
    void testConvertAndValidateRole_ValidRole_Manager() throws Exception {
        // Act
        Long result = validation.convertAndValidateRole("MANAGER");

        // Assert
        assertNotNull(result);
        assertEquals(3L, result);
    }

    @Test
    void testConvertAndValidateRole_InvalidRole() {
        // Act & Assert
        UnAuthorizedUserException thrown = assertThrows(UnAuthorizedUserException.class, () -> {
            validation.convertAndValidateRole("INVALID_ROLE");
        });

        assertEquals("Invalid role. Accepted roles are ADMIN, USER, MANAGER", thrown.getMessage());
        assertEquals(400, thrown.getStatus());
    }

    @Test
    void testValidateRoles_NullRole() {
        // Act & Assert
        InvalidRoleException thrown = assertThrows(InvalidRoleException.class, () -> {
            validation.validateRoles(null);
        });

        assertEquals("Role cannot be null or empty", thrown.getMessage());
        assertEquals(400, thrown.getStatus());
    }

    @Test
    void testValidateRoles_EmptyRole() {
        // Act & Assert
        InvalidRoleException thrown = assertThrows(InvalidRoleException.class, () -> {
            validation.validateRoles("");
        });

        assertEquals("Role cannot be null or empty", thrown.getMessage());
        assertEquals(400, thrown.getStatus());
    }
    @Test
    void testValidateRoles_RoleTooShort() {
        // Act & Assert
        InvalidRoleException thrown = assertThrows(InvalidRoleException.class, () -> {
            validation.validateRoles("US");
        });

        assertEquals("Role length must be between 3 and 50 characters", thrown.getMessage());
        assertEquals(400, thrown.getStatus());
    }

    @Test
    void testValidateRoles_RoleTooLong() {
        // Act & Assert
        InvalidRoleException thrown = assertThrows(InvalidRoleException.class, () -> {
            validation.validateRoles("A".repeat(51)); // A string with 51 characters
        });

        assertEquals("Role length must be between 3 and 50 characters", thrown.getMessage());
        assertEquals(400, thrown.getStatus());
    }


}
