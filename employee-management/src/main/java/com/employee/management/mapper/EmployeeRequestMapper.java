package com.employee.management.mapper;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.management.dto.EmployeeManagementRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EmployeeRequestMapper {

    @Mappings({
            @Mapping(source = "first_name", target = "name"),
            @Mapping(source = "role", target = "role_id")
    })
    EmployeeDatabaseRequestDTO toEmployeeDatabaseRequest(EmployeeManagementRequestDTO employeeManagementRequestDTO);

   @Named("combineFirstAndLastName")
    default String combineFirstAndLastName(EmployeeManagementRequestDTO employeeRequestDTO) {
        return employeeRequestDTO.getFirst_name() + " " + employeeRequestDTO.getSurname();
    }
}
