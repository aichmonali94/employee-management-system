package com.employee.management.service;

import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;


public interface EmployeeManagementService {

    EmployeeManagementResponseDTO createEmployee(EmployeeManagementRequestDTO employeeRequestDTO, String role);
    EmployeeManagementResponseDTO getEmployeeById(Long id);
    EmployeeManagementResponseDTO updateEmployee(Long id, EmployeeManagementRequestDTO employeeRequestDTO, String role);
    String deleteEmployee(Long id);
}
