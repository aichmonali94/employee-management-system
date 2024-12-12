package com.employee.database.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDatabaseRequestDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Role id cannot be empty")
    private Long role_id;

}
