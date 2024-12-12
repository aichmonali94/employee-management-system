package com.employee.management.model;

import java.util.EnumSet;

public enum ValidRoles {
    USER, ADMIN, MANAGER;

    public static boolean isValidRole(String role) {

        return EnumSet.allOf(ValidRoles.class).stream().anyMatch(
                validRole -> validRole.name().equalsIgnoreCase(role)
        );
    }
}
