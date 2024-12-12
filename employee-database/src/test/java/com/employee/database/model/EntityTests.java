package com.employee.database.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityTests {

    private Project project;
    private Role role;
    private Employee employee1;
    List<Employee> employees;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        // Initialize Project and Employee objects before each test
        project = new Project();
        role = new Role();
        employee1 = new Employee();
        employee2 = new Employee();
        employees = new ArrayList<>();

        employee1.setEmployeeId(1L);  // Set an ID for the employee
        employee1.setName("John Doe");
        employee2.setEmployeeId(2L);  // Set an ID for the employee
        employee2.setName("User Two");

        employees.add(employee1);
        employees.add(employee2);

        role.setName("ADMIN");
        role.setId(30L);
        role.setEmployees(employees);

        // Set the employee for the project
        project.setId(1L);
        project.setName("Project A");
        project.setEmployee(employee1);
    }

    // Test if getters and setters work for Project fields
    @Test
    void testProjectRoleFields() {
        // Assert project & Role ID
        assertEquals(1L, project.getId());
        assertEquals(30L, role.getId());

        // Assert project & Role name
        assertEquals("Project A", project.getName());
        assertEquals("ADMIN", role.getName());

        // Assert employee relationship
        assertNotNull(project.getEmployee());
        assertNotNull(role.getEmployees());
        assertEquals(1L, project.getEmployee().getEmployeeId());
        assertEquals("John Doe", project.getEmployee().getName());
    }

    // Test if the name of the project is set correctly
    @Test
    void testSetName() {
        project.setName("New Project");
        assertEquals("New Project", project.getName());
    }

    // Test if the employee is assigned correctly to the project
    @Test
    void testSetEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeId(2L);
        newEmployee.setName("Jane Doe");

        project.setEmployee(newEmployee);

        assertNotNull(project.getEmployee());
        assertEquals(2L, project.getEmployee().getEmployeeId());
        assertEquals("Jane Doe", project.getEmployee().getName());
    }

    // Test if the project ID can be correctly set
    @Test
    void testSetProjectId() {
        project.setId(2L);
        assertEquals(2L, project.getId());
    }

    // Test if the project and employee relationship is maintained correctly
    @Test
    void testEmployeeRelationship() {
        // Initially, the employee is set
        assertNotNull(project.getEmployee());
        assertEquals(1L, project.getEmployee().getEmployeeId());

        // Change employee
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeId(3L);
        newEmployee.setName("Alice Doe");

        project.setEmployee(newEmployee);

        assertNotNull(project.getEmployee());
        assertEquals(3L, project.getEmployee().getEmployeeId());
        assertEquals("Alice Doe", project.getEmployee().getName());
    }
}
