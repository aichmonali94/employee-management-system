package com.employee.management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmployeeManagementResponseDTO {

    @JsonProperty(value = "id")
    private Long employeeId;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "surname")
    private String surname;

    @JsonProperty(value = "role_id")
    private Long roleId;
}
