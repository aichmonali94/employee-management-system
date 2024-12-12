package com.employee.management.service;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import com.employee.management.mapper.EmployeeDatabaseResponseMapper;
import com.employee.management.mapper.EmployeeManagementRequestMapper;
import com.employee.management.mapper.EmployeeRequestMapper;
import com.employee.management.service.impl.EmployeeManagementServiceImpl;
import com.employee.management.validation.EmployeeManagementValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeManagementServiceImplTest {

    @InjectMocks
    private EmployeeManagementServiceImpl employeeManagementService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmployeeManagementRequestMapper mapperRequest;

    @Mock
    private EmployeeManagementValidation validation;

    @Mock
    private EmployeeDatabaseResponseMapper mapperResponse;

    @Value("${employee.database.api.url}")
    private String EMPLOYEE_DATABASE_URL;

    private EmployeeManagementRequestDTO employeeRequestDTO;
    private EmployeeManagementResponseDTO employeeResponseDTO;
    private EmployeeDatabaseResponseDTO employeeDatabaseResponseDTO;

    @BeforeEach
    void setUp() {
        // Initialize objects for each test case
        employeeRequestDTO = new EmployeeManagementRequestDTO();
        employeeRequestDTO.setFirst_name("John");
        employeeRequestDTO.setSurname("Doe");
        employeeRequestDTO.setRole(30L);

        employeeResponseDTO = new EmployeeManagementResponseDTO();
        employeeResponseDTO.setEmployeeId(1L);
        employeeResponseDTO.setFirstName("John");
        employeeResponseDTO.setSurname("Doe");
        employeeResponseDTO.setRoleId(2L);

        employeeDatabaseResponseDTO = new EmployeeDatabaseResponseDTO();
        employeeDatabaseResponseDTO.setEmployeeId(1L);
        employeeDatabaseResponseDTO.setName("John Doe");
        employeeDatabaseResponseDTO.setRoleId(2L);
    }

    @Test
    void testCreateEmployee() {
        // Arrange
        when(validation.convertAndValidateRole("USER")).thenReturn(2L);
        when(mapperRequest.toEmployeeDatabaseRequest(employeeRequestDTO)).thenReturn(new EmployeeDatabaseRequestDTO());
        when(restTemplate.postForObject(anyString(), any(), eq(EmployeeDatabaseResponseDTO.class))).thenReturn(employeeDatabaseResponseDTO);
        when(mapperResponse.toEmployeeManagementResponse(employeeDatabaseResponseDTO)).thenReturn(employeeResponseDTO);

        // Act
        EmployeeManagementResponseDTO result = employeeManagementService.createEmployee(employeeRequestDTO, "USER");

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getSurname());
        assertEquals(1L, result.getEmployeeId());
    }

    @Test
    void testGetEmployeeById_EmployeeExists() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(EmployeeDatabaseResponseDTO.class))).thenReturn(employeeDatabaseResponseDTO);
        when(mapperResponse.toEmployeeManagementResponse(employeeDatabaseResponseDTO)).thenReturn(employeeResponseDTO);

        // Act
        EmployeeManagementResponseDTO result = employeeManagementService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        when(validation.convertAndValidateRole("USER")).thenReturn(2L);
        when(mapperRequest.toEmployeeDatabaseRequest(employeeRequestDTO)).thenReturn(new EmployeeDatabaseRequestDTO());
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), eq(EmployeeDatabaseResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(employeeDatabaseResponseDTO, HttpStatus.OK));
        when(mapperResponse.toEmployeeManagementResponse(employeeDatabaseResponseDTO)).thenReturn(employeeResponseDTO);

        // Act
        EmployeeManagementResponseDTO result = employeeManagementService.updateEmployee(1L, employeeRequestDTO, "USER");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getEmployeeId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT));

        // Act
        String result = employeeManagementService.deleteEmployee(1L);

        // Assert
        assertEquals("Deleted", result);
    }
}
