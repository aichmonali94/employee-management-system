package com.employee.database.controller;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.database.exception.NotFoundException;
import com.employee.database.service.EmployeeDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Base64;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class EmployeeDatabaseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeDatabaseService employeeDatabaseService;

    @InjectMocks
    private EmployeeDatabaseController employeeDatabaseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeDatabaseController).build();
    }


    // Test createEmployee() - Success
    @Test
    void testCreateEmployeeSuccess() throws Exception {
        EmployeeDatabaseRequestDTO requestDTO = new EmployeeDatabaseRequestDTO("John Doe", 30L);
        EmployeeDatabaseResponseDTO responseDTO = new EmployeeDatabaseResponseDTO(1L, "John Doe", 30L);

        when(employeeDatabaseService.saveEmployee(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/database/v1/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"role_id\":30}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.role_id").value(30));

        verify(employeeDatabaseService, times(1)).saveEmployee(requestDTO);
    }

    // Test createEmployee() - Validation Error
    @Test
    void testCreateEmployeeValidationError() throws Exception {
        mockMvc.perform(post("/database/v1/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"role_id\":30}"))
                .andExpect(status().isBadRequest());

        verify(employeeDatabaseService, times(0)).saveEmployee(any());
    }

    // Test getEmployeeById() - Success
    @Test
    void testGetEmployeeByIdSuccess() throws Exception {
        EmployeeDatabaseResponseDTO responseDTO = new EmployeeDatabaseResponseDTO(1L, "John Doe", 30L);

        when(employeeDatabaseService.fetchEmployeeById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/database/v1/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.role_id").value(30));

        verify(employeeDatabaseService, times(1)).fetchEmployeeById(1L);
    }

    // Test updateEmployee() - Success
    @Test
    void testUpdateEmployeeSuccess() throws Exception {
        EmployeeDatabaseRequestDTO requestDTO = new EmployeeDatabaseRequestDTO("John Doe", 31L);
        EmployeeDatabaseResponseDTO responseDTO = new EmployeeDatabaseResponseDTO(1L, "John Doe", 31L);

        when(employeeDatabaseService.updateEmployee(eq(1L), any(EmployeeDatabaseRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/database/v1/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"role_id\":31}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.role_id").value(31));

        verify(employeeDatabaseService, times(1)).updateEmployee(eq(1L), any(EmployeeDatabaseRequestDTO.class));
    }

    // Test deleteEmployee() - Success
    @Test
    void testDeleteEmployeeSuccess() throws Exception {
        doNothing().when(employeeDatabaseService).deleteEmployee(1L);

        mockMvc.perform(delete("/database/v1/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));

        verify(employeeDatabaseService, times(1)).deleteEmployee(1L);
    }
}
