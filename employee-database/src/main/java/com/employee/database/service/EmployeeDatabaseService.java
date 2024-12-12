package com.employee.database.service;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;

public interface EmployeeDatabaseService {

    EmployeeDatabaseResponseDTO saveEmployee(EmployeeDatabaseRequestDTO employeeRequestDTO);
    EmployeeDatabaseResponseDTO fetchEmployeeById(Long id);
    EmployeeDatabaseResponseDTO updateEmployee(Long id, EmployeeDatabaseRequestDTO employeeRequestDTO);
    void deleteEmployee(Long id);
}
