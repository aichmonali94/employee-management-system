package com.employee.database.service;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.database.exception.NotFoundException;
import com.employee.database.model.Employee;
import com.employee.database.model.Role;
import com.employee.database.repository.EmployeeRepository;
import com.employee.database.repository.ProjectRepository;
import com.employee.database.repository.RoleRepository;
import com.employee.database.service.impl.EmployeeDatabaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeDatabaseServiceImplTest {

    @InjectMocks
    private EmployeeDatabaseServiceImpl employeeDatabaseService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProjectRepository projectRepository;


    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test saveEmployee method
    @Test
    void testSaveEmployee() {
        EmployeeDatabaseRequestDTO requestDTO = new EmployeeDatabaseRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setRole_id(1L);

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("John Doe");
        employee.setRole(role);

        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDatabaseResponseDTO response = employeeDatabaseService.saveEmployee(requestDTO);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals(1L, response.getRoleId());
        verify(roleRepository, times(1)).save(any(Role.class));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    // Test fetchEmployeeById method - Employee exists
    @Test
    void testFetchEmployeeById() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("John Doe");
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        employee.setRole(role);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeDatabaseResponseDTO response = employeeDatabaseService.fetchEmployeeById(1L);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals(1L, response.getRoleId());
    }

    // Test fetchEmployeeById method - Employee not found
    @Test
    void testFetchEmployeeByIdNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            employeeDatabaseService.fetchEmployeeById(1L);
        });

        assertEquals("No Employee Found", exception.getMessage());
    }

    // Test updateEmployee method
    @Test
    void testUpdateEmployee() {
        EmployeeDatabaseRequestDTO requestDTO = new EmployeeDatabaseRequestDTO();
        requestDTO.setName("John Updated");
        requestDTO.setRole_id(2L);

        Role role = new Role();
        role.setId(2L);
        role.setName("USER");

        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("John Updated");
        employee.setRole(role);

        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDatabaseResponseDTO response = employeeDatabaseService.updateEmployee(1L, requestDTO);

        assertNotNull(response);
        assertEquals("John Updated", response.getName());
        assertEquals(2L, response.getRoleId());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    // Test deleteEmployee method - Employee exists
    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("John Doe");
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        employee.setRole(role);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeDatabaseService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
        verify(jdbcTemplate, times(1)).update("CALL delete_role(?)", role.getId());
    }

    // Test deleteEmployee method - Employee not found
    @Test
    void testDeleteEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            employeeDatabaseService.deleteEmployee(1L);
        });

        assertEquals("No Employee Found", exception.getMessage());
    }

}
