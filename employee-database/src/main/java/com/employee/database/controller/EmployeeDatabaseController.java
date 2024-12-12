package com.employee.database.controller;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.database.exception.NotFoundException;
import com.employee.database.service.EmployeeDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/database/v1/api")
public class EmployeeDatabaseController {

    @Autowired
    private EmployeeDatabaseService service;

    @PostMapping(value="/employees")
    public ResponseEntity<EmployeeDatabaseResponseDTO> createEmployee(@RequestBody @Validated EmployeeDatabaseRequestDTO employeeRequestDTO){
        return new ResponseEntity<>(service.saveEmployee(employeeRequestDTO),HttpStatus.CREATED);
    }

    @GetMapping(value="/employees/{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeDatabaseResponseDTO> getEmployeeById(@PathVariable("employeeId") Long employeeId){
        return new ResponseEntity<>(service.fetchEmployeeById(employeeId), HttpStatus.OK);
    }

    @PutMapping(value="/employees/{employeeId}", consumes={MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EmployeeDatabaseResponseDTO updateEmployee(@PathVariable Long employeeId,
                                                                      @RequestBody EmployeeDatabaseRequestDTO employeeRequestDTO){
        return Optional.ofNullable(service.updateEmployee(employeeId, employeeRequestDTO))
                .orElseThrow(() -> new NotFoundException("Employee Not Found", 404, LocalDateTime.now()));
    }

    @DeleteMapping(value="/employees/{employeeId}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long employeeId){
        try {
            service.deleteEmployee(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body("Employee deleted successfully");
        } catch (NotFoundException ex) {
            throw new NotFoundException("Employee Not Found", 404, LocalDateTime.now());
        }
    }
}
