package com.employee.management.mapper;

import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDatabaseResponseMapper {

    public EmployeeManagementResponseDTO toEmployeeManagementResponse(EmployeeDatabaseResponseDTO employeeDatabaseResponseDTO){
        EmployeeManagementResponseDTO dto = new EmployeeManagementResponseDTO();
        dto = splitFirstAndLastName(employeeDatabaseResponseDTO);
        dto.setRoleId(employeeDatabaseResponseDTO.getRoleId());
        dto.setEmployeeId(employeeDatabaseResponseDTO.getEmployeeId());
        return dto;
    }

    static EmployeeManagementResponseDTO splitFirstAndLastName(EmployeeDatabaseResponseDTO employeeDatabaseResponseDTO) {

        EmployeeManagementResponseDTO dto = new EmployeeManagementResponseDTO();
        if (employeeDatabaseResponseDTO.getName() == null || employeeDatabaseResponseDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty.");
        }

        // Split the full name by space
        String[] nameParts = employeeDatabaseResponseDTO.getName().trim().split("\\s+");

        // Assign first and last name
        if (nameParts.length == 1) {
            dto.setFirstName(nameParts[0]);
            dto.setSurname("");  // Empty if no last name
        } else if (nameParts.length == 2) {
            dto.setFirstName(nameParts[0]);
            dto.setSurname(nameParts[1]);
        } else {
            String firstName = nameParts[0];
            String middleName = String.join(" ", java.util.Arrays.copyOfRange(nameParts, 1, nameParts.length - 1));
            dto.setFirstName(firstName+middleName);
            dto.setSurname(nameParts[nameParts.length - 1]);
        }
        return dto;
    }
}
