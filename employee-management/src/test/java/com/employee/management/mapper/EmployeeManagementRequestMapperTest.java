package com.employee.management.mapper;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EmployeeManagementRequestMapperTest {

    private EmployeeManagementRequestMapper mapper;

    @BeforeEach
    void setUp() {
        // Initialize the mapper before each test
        mapper = new EmployeeManagementRequestMapper();
    }

    @Test
    void testToEmployeeDatabaseRequest() {
        // Arrange: Create the EmployeeManagementRequestDTO with test data
        EmployeeManagementRequestDTO requestDTO = new EmployeeManagementRequestDTO();
        requestDTO.setFirst_name("John");
        requestDTO.setSurname("Doe");
        requestDTO.setRole(1L); // Assuming role 1 corresponds to "ADMIN" for example

        // Act: Use the mapper to convert it to EmployeeDatabaseRequestDTO
        EmployeeDatabaseRequestDTO databaseRequestDTO = mapper.toEmployeeDatabaseRequest(requestDTO);

        // Assert: Check if the values were correctly mapped
        assertEquals("John Doe", databaseRequestDTO.getName(), "Full name should be correctly combined.");
        assertEquals(1L, databaseRequestDTO.getRole_id(), "Role ID should be correctly mapped.");
    }

}
