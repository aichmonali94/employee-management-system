CREATE PROCEDURE delete_role(IN role_id INT)
BEGIN
    -- Reassign all projects to a default employee (employee with id 1 in this case)
    UPDATE project SET employee_id = 1 WHERE employee_id IN (SELECT id FROM employee WHERE role_id = role_id);
    UPDATE employee SET ROLE_ID = NULL WHERE ROLE_ID = role_id;

    -- Delete the role itself
    DELETE FROM role WHERE id = role_id;

     -- Delete the employees with the given role
        DELETE FROM employee WHERE role_id = NULL;
END;