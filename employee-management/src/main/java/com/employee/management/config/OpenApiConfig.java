package com.employee.management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.annotations.security.SecuritySchemeType;


@OpenAPIDefinition(info =
@Info(title = "Employee Management System",
        version = "1.0.0",
        description = "Employment Management System consists of two distinct Spring Boot applications i.e. employee-management & employee-database. Employee-management application is designed to streamline the management of employees, projects, and roles within an organization. It enforces role-based access control to ensure that only authorized users can perform certain operations on employees. Employee-database application handles the backend operations and facilitates CRUD (Create, Read, Update, Delete) operations for employees and uses H2 in-memory database using Spring Data JPA.",
        contact = @Contact(email = "aichmonali@outlook.com", name = "Monali Aich")),
        servers = {
                @Server(url = "https://employee-management-api/com/v1", description = "Employee Management Production"),
                @Server(url = "http://localhost:8002/staging/employee-management/v1", description = "Employee Management Staging"),
                @Server(url = "http://localhost:8001/dev/employee-management/v1", description = "Employee Management Dev"),
                //@Server(url = "https://employee-database-api/com/v1", description = "Employee Database Production"),
                //@Server(url = "http://localhost:9002/staging-api/employee-database/v1", description = "Employee Database Staging"),
                //@Server(url = "http://localhost:9001/dev-api/employee-database/v1", description = "Employee Database Dev")
        },
        security = {
                @SecurityRequirement(name = "RoleBasedAuth")
        }
)
/*@SecurityScheme(
        name = "RoleBasedAuth",
        //type = SecuritySchemeType.APIKEY,
        //in = io.swagger.v3.oas.annotations.security.SecuritySchemeIn.HEADER,
        paramName = "Authorization",
        description = "Role-based authentication. Use the value 'ADMIN' or 'USER' or MANAGER in the header."
)*/
public class OpenApiConfig {


}
