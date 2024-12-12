package com.employee.management.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmployeeManagementRequestDTO {

    @NotEmpty(message = "First name cannot be empty")
    private String first_name;

    @NotEmpty(message = "Surname cannot be empty")
    private String surname;

    private Long role;
}
