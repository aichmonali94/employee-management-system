package com.employee.management.controller;

import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import com.employee.management.service.EmployeeManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeManagementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeManagementService service;

    @InjectMocks
    private EmployeeManagementController controller;

    private EmployeeManagementRequestDTO employeeRequestDTO;
    private EmployeeManagementResponseDTO employeeResponseDTO;
    private EmployeeManagementResponseDTO employeeResponseDTONew;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        // Create a mock employee request and response DTO
        employeeRequestDTO = new EmployeeManagementRequestDTO();
        employeeRequestDTO.setFirst_name("John");
        employeeRequestDTO.setSurname("Doe");
        employeeRequestDTO.setRole(30L);

        employeeResponseDTO = new EmployeeManagementResponseDTO();
        employeeResponseDTO.setEmployeeId(1L);
        employeeResponseDTO.setFirstName("John");
        employeeResponseDTO.setSurname("Doe");
        employeeResponseDTO.setRoleId(30L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateEmployee() throws Exception {
        when(service.createEmployee(Mockito.any(EmployeeManagementRequestDTO.class), Mockito.anyString()))
                .thenReturn(employeeResponseDTO);

        MvcResult result = mockMvc.perform(post("/employee-management/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Role", "ADMIN")
                        .content("{\"first_name\":\"John\", \"surname\":\"Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.role_id").value(30L))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetEmployeeById() throws Exception {
        when(service.getEmployeeById(1L)).thenReturn(employeeResponseDTO);

        mockMvc.perform(get("/employee-management/v1/employees/{employeeId}", 1L)
                        .header("Role", "USER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.role_id").value(30L))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testUpdateEmployee() throws Exception {

        employeeResponseDTONew = new EmployeeManagementResponseDTO();
        employeeResponseDTONew.setEmployeeId(1L);
        employeeResponseDTONew.setFirstName("User");
        employeeResponseDTONew.setSurname("One");
        employeeResponseDTONew.setRoleId(32L);

        when(service.updateEmployee(1L, employeeRequestDTO, "MANAGER"))
                .thenReturn(employeeResponseDTONew);

        mockMvc.perform(put("/employee-management/v1/employees/{employeeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Role", "MANAGER")
                        .content("{\"first_name\":\"User\", \"surname\":\"One\"}"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteEmployee() throws Exception {
        when(service.deleteEmployee(1L)).thenReturn("Employee deleted successfully");

        mockMvc.perform(delete("/employee-management/v1/delete/employees/{employeeId}", 1L)
                        .header("Role", "ADMIN"))
                .andExpect(status().isNoContent());
    }

}
