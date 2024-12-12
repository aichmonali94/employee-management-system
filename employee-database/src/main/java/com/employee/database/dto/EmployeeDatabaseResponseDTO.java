package com.employee.database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDatabaseResponseDTO {

    @JsonProperty(value = "id")
    private Long employeeId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "role_id")
    private Long roleId;
}
