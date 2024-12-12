package com.employee.management.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvalidRoleException extends RuntimeException{

    private String message;
    private int status;
    private LocalDateTime timestamp;

    public InvalidRoleException(String message) {
        super(message);
    }
}
