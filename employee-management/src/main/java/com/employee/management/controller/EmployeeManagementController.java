package com.employee.management.controller;

import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import com.employee.management.exception.AccessForbiddenException;
import com.employee.management.exception.UnAuthorizedUserException;
import com.employee.management.service.EmployeeManagementService;
import com.employee.management.validation.EmployeeManagementValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/employee-management/v1")
public class EmployeeManagementController {

    @Autowired
    private EmployeeManagementService service;

    @Operation(summary = "Add new employee", description = "API to add a new employee in an organization.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid role or data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not allowed"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Access denied for the provided role")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value="/employees", consumes={MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeManagementResponseDTO> createEmployee(@RequestBody @Validated EmployeeManagementRequestDTO employeeRequestDTO,
                          @RequestHeader("Role") String role) throws Exception, AccessForbiddenException, UnAuthorizedUserException {
        return new ResponseEntity<>(service.createEmployee(employeeRequestDTO, role), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve Employee Details by ID", description = "API to retrieve employee information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Employee details Fetched"),
            @ApiResponse(responseCode = "404", description = "Not Found - Employee with given ID does not exist"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid role or data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not allowed"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Access denied for the provided role")
    })
    @PreAuthorize("hasRole('ADMIN','USER','MANAGER')")
    @GetMapping(value="/employees/{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeManagementResponseDTO> getEmployeeById(@PathVariable("employeeId") Long employeeId,
                       @RequestHeader("Role") String role){

        return new ResponseEntity<>(service.getEmployeeById(employeeId), HttpStatus.OK);
    }

    @Operation(summary = "Add new employee", description = "API to add a new employee in an organization.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid role or data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not allowed"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Access denied for the provided role")
    })
    @PreAuthorize("hasRole('ADMIN','USER','MANAGER')")
    @PutMapping(value="/employees/{employeeId}", consumes={MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeManagementResponseDTO> updateEmployee(@PathVariable("employeeId") Long employeeId,
            @Validated @RequestBody EmployeeManagementRequestDTO employeeRequestDTO,
         @RequestHeader("Role") String role){
        return new ResponseEntity<>(service.updateEmployee(employeeId,employeeRequestDTO,role), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Retrieve Employee Details by ID", description = "API to retrieve employee information by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Employee details Fetched"),
            @ApiResponse(responseCode = "404", description = "Not Found - Employee with given ID does not exist"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid role or data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not allowed"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Access denied for the provided role")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="delete/employees/{employeeId}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("employeeId") Long employeeId, @RequestHeader("Role") String role){
        return new ResponseEntity<>(service.deleteEmployee(employeeId), HttpStatus.valueOf(204));
    }

}
