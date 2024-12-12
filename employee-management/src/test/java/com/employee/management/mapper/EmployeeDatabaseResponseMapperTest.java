package com.employee.management.mapper;

import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EmployeeDatabaseResponseMapperTest {

    private EmployeeDatabaseResponseMapper mapper;

    @BeforeEach
    void setUp() {
        // Initialize the mapper before each test
        mapper = new EmployeeDatabaseResponseMapper();
    }

    @Test
    void testToEmployeeManagementResponse_ValidName() {
        // Arrange
        EmployeeDatabaseResponseDTO databaseResponseDTO = new EmployeeDatabaseResponseDTO();
        databaseResponseDTO.setEmployeeId(1L);
        databaseResponseDTO.setRoleId(2L);
        databaseResponseDTO.setName("John Doe");

        // Act
        EmployeeManagementResponseDTO result = mapper.toEmployeeManagementResponse(databaseResponseDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
        assertEquals(2L, result.getRoleId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getSurname());
    }

    @Test
    void testToEmployeeManagementResponse_SingleName() {
        // Arrange
        EmployeeDatabaseResponseDTO databaseResponseDTO = new EmployeeDatabaseResponseDTO();
        databaseResponseDTO.setEmployeeId(1L);
        databaseResponseDTO.setRoleId(2L);
        databaseResponseDTO.setName("John");

        // Act
        EmployeeManagementResponseDTO result = mapper.toEmployeeManagementResponse(databaseResponseDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
        assertEquals(2L, result.getRoleId());
        assertEquals("John", result.getFirstName());
        assertEquals("", result.getSurname());
    }
}
