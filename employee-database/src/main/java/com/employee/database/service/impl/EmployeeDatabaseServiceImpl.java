package com.employee.database.service.impl;

import com.employee.database.dto.EmployeeDatabaseRequestDTO;
import com.employee.database.dto.EmployeeDatabaseResponseDTO;
import com.employee.database.exception.NotFoundException;
import com.employee.database.model.Employee;
import com.employee.database.model.Role;
import com.employee.database.repository.EmployeeRepository;
import com.employee.database.repository.ProjectRepository;
import com.employee.database.repository.RoleRepository;
import com.employee.database.service.EmployeeDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmployeeDatabaseServiceImpl implements EmployeeDatabaseService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public EmployeeDatabaseResponseDTO saveEmployee(EmployeeDatabaseRequestDTO employeeRequestDTO) {
        //Saving ROLE details
        Role roleSave = saveRoleEntity(employeeRequestDTO);

        //Saving Employee Details
        Employee empSaved = saveEmployeeEntity(employeeRequestDTO,roleSave);

        return mapEmployeeEntityToResponse(empSaved,roleSave);
    }

    @Override
    public EmployeeDatabaseResponseDTO fetchEmployeeById(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            return mapEntityToResponse(employeeOpt.get());
        } else {
            throw new NotFoundException("No Employee Found", 404, LocalDateTime.now());
        }
    }

    @Override
    public EmployeeDatabaseResponseDTO updateEmployee(Long employeeId, EmployeeDatabaseRequestDTO employeeRequestDTO) {
        //Saving ROLE details
        Role roleSave = saveRoleEntity(employeeRequestDTO);

        //Updating the existing Employee Details
        Employee empUpdated = updateEmployeeEntity(employeeId,employeeRequestDTO,roleSave);

        return mapEmployeeEntityToResponse(empUpdated,roleSave);
    }

    @Transactional
    @Override
    public void deleteEmployee(Long employeeId) {

        employeeRepository.findById(employeeId)
                .ifPresentOrElse(
                        employee -> {
                            employeeRepository.deleteById(employeeId);
                            jdbcTemplate.update("CALL delete_role(?)", employee.getRole().getId());
                        },
                        () -> { throw new NotFoundException("No Employee Found", 404, LocalDateTime.now()); }
                );
    }

    private Role saveRoleEntity(EmployeeDatabaseRequestDTO requestDTO){
        Role role = new Role();
        role.setId(requestDTO.getRole_id());
        if(requestDTO.getRole_id() == 1){
            role.setName("ADMIN");
        }else if(requestDTO.getRole_id() == 2){
            role.setName("USER");
        }else if(requestDTO.getRole_id() ==3){
            role.setName("MANAGER");
        }

        return roleRepository.save(role);
    }

    private Employee saveEmployeeEntity(EmployeeDatabaseRequestDTO requestDTO, Role role){
        Employee employee = new Employee();
        employee.setName(requestDTO.getName());
        employee.setRole(role);

        return employeeRepository.save(employee);
    }

    private EmployeeDatabaseResponseDTO mapEmployeeEntityToResponse(Employee employee, Role roleSave){

        EmployeeDatabaseResponseDTO response = new EmployeeDatabaseResponseDTO();
        response.setEmployeeId(employee.getEmployeeId());
        response.setName(employee.getName());
        response.setRoleId(roleSave.getId());
        return response;
    }

    private EmployeeDatabaseResponseDTO mapEntityToResponse(Employee employee){

        EmployeeDatabaseResponseDTO response = new EmployeeDatabaseResponseDTO();
        response.setEmployeeId(employee.getEmployeeId());
        response.setName(employee.getName());
        response.setRoleId(employee.getRole().getId());
        return response;
    }

    private Employee updateEmployeeEntity(Long employeeId, EmployeeDatabaseRequestDTO requestDTO, Role role){
        Optional<Employee> employeeFetch = Optional.ofNullable(employeeRepository.findById(employeeId).orElse(null));
        return employeeFetch.map(employee -> {
            employee.setName(requestDTO.getName());
            employee.setRole(role);
            return employeeRepository.save(employee); // Save and return the updated employee
        }).orElse(new Employee());
    }
}
