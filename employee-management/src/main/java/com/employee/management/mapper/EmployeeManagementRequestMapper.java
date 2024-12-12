package com.employee.management.mapper;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.management.dto.EmployeeManagementRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeManagementRequestMapper {

    public EmployeeDatabaseRequestDTO toEmployeeDatabaseRequest(EmployeeManagementRequestDTO employeeManagementRequestDTO){
        EmployeeDatabaseRequestDTO dto = new EmployeeDatabaseRequestDTO();
        dto.setName(combineFirstAndLastName(employeeManagementRequestDTO));
        dto.setRole_id(employeeManagementRequestDTO.getRole());
        return dto;
    }

    static String combineFirstAndLastName(EmployeeManagementRequestDTO employeeRequestDTO) {
        return employeeRequestDTO.getFirst_name() + " " + employeeRequestDTO.getSurname();
    }
}
