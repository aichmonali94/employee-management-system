package com.employee.management.service.impl;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.management.dto.EmployeeManagementRequestDTO;
import com.employee.management.dto.EmployeeManagementResponseDTO;
import com.employee.management.exception.NotFoundException;
import com.employee.management.mapper.EmployeeDatabaseResponseMapper;
import com.employee.management.mapper.EmployeeManagementRequestMapper;
import com.employee.management.mapper.EmployeeRequestMapper;
import com.employee.management.service.EmployeeManagementService;
import com.employee.management.validation.EmployeeManagementValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeManagementRequestMapper mapperRequest;

    @Autowired
    private EmployeeManagementValidation validation;

    @Autowired
    private EmployeeDatabaseResponseMapper mapperResponse;

    @Value("${employee.database.api.url}")
    private String EMPLOYEE_DATABASE_URL;

    @Override
    public EmployeeManagementResponseDTO createEmployee(EmployeeManagementRequestDTO employeeRequestDTO, String role) {
        return mapperResponse.toEmployeeManagementResponse(callPostRestEndpoint(employeeRequestDTO,role));
    }

    @Override
    public EmployeeManagementResponseDTO getEmployeeById(Long employeeId) {
        return  mapperResponse.toEmployeeManagementResponse(callGetByIdRestEndpoint(employeeId));
    }

    @Override
    public EmployeeManagementResponseDTO updateEmployee(Long employeeId, EmployeeManagementRequestDTO employeeRequestDTO, String role) {
        return mapperResponse.toEmployeeManagementResponse(callPutRestEndpoint(employeeId,employeeRequestDTO,role));
    }

    @Override
    public String deleteEmployee(Long employeeId) {
        return callDeleteRestEndpoint(employeeId);
    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    private EmployeeDatabaseResponseDTO callPostRestEndpoint(EmployeeManagementRequestDTO employeeRequestDTO, String role ){

        //Map the Role {ADMIN,USER,MANAGER} to Database Value {1,2,3}
        Long roleValue = validation.convertAndValidateRole(role);

        //Adding the endpoint of App2 - 'Employee Database'
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(EMPLOYEE_DATABASE_URL+ "/api/employees");

        // Map App1's EmployeeRequest to App2's EmployeeRequest using the mapper
        employeeRequestDTO.setRole(roleValue);
        EmployeeDatabaseRequestDTO employeeDatabaseRequest = mapperRequest.toEmployeeDatabaseRequest(employeeRequestDTO);

        //Calling the App2 endpoint to store the data in H2 database
        return restTemplate.postForObject(uriBuilder.toUriString(), employeeDatabaseRequest, EmployeeDatabaseResponseDTO.class);

    }

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    private EmployeeDatabaseResponseDTO callGetByIdRestEndpoint(Long employeeId ){

        //Adding the endpoint of App2 - 'Employee Database'
        String url = UriComponentsBuilder
                .fromUriString(EMPLOYEE_DATABASE_URL+ "/api/employees/{employeeId}")
                .buildAndExpand(employeeId)
                .toUriString();

        //Calling the App2 endpoint to fetch the data from H2 database
        return restTemplate.getForObject(url, EmployeeDatabaseResponseDTO.class);
    }

    private EmployeeDatabaseResponseDTO callPutRestEndpoint(Long employeeId,EmployeeManagementRequestDTO employeeRequestDTO, String role ){

        //Map the Role {ADMIN,USER,MANAGER} to Database Value {1,2,3}
        Long roleValue = validation.convertAndValidateRole(role);

        //Adding the endpoint of App2 - 'Employee Database'
        String url = UriComponentsBuilder
                .fromUriString(EMPLOYEE_DATABASE_URL+ "/api/employees/{employeeId}")
                .buildAndExpand(employeeId)
                .toUriString();

        // Map App1's EmployeeRequest to App2's EmployeeRequest using the mapper
        employeeRequestDTO.setRole(roleValue);
        EmployeeDatabaseRequestDTO employeeDatabaseRequest = mapperRequest.toEmployeeDatabaseRequest(employeeRequestDTO);

        //Calling the App2 endpoint to store the data in H2 database
        HttpEntity<EmployeeDatabaseRequestDTO> databaseRequestDto = new HttpEntity<>(employeeDatabaseRequest);
        return restTemplate.exchange(url, HttpMethod.PUT, databaseRequestDto, EmployeeDatabaseResponseDTO.class).getBody();
    }

    private String callDeleteRestEndpoint(Long employeeId){
        String url = UriComponentsBuilder
                .fromUriString(EMPLOYEE_DATABASE_URL+ "/api/employees/{employeeId}")
                .buildAndExpand(employeeId)
                .toUriString();

        return restTemplate.exchange(url, HttpMethod.DELETE,null, String.class).getBody();
    }
}
