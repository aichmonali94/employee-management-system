INSERT INTO role (role_id, name) VALUES (1,'ADMIN');
INSERT INTO role (role_id, name) VALUES (2,'USER');

INSERT INTO employee (employee_id, name, role_id) VALUES (101, 'User One', 1);
INSERT INTO employee (employee_id, name, role_id) VALUES (102, 'User Two', 2);

INSERT INTO project (project_id, name, employee_id) VALUES (1001, 'Project A', 101);
INSERT INTO project (project_id, name, employee_id) VALUES (1002, 'Project B', 102);

-- Employee Table (Assuming primary key 'id' is auto-created)
CREATE INDEX idx_employee_roleid ON Employee(role_id);

-- Project Table (Assuming primary key 'id' is auto-created)
CREATE INDEX idx_project_employee_id ON Project(employee_id);

-- Role Table (Assuming primary key 'id' is auto-created)
CREATE INDEX idx_role_name ON Role(name);
