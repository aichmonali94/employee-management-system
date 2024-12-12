package com.employee.management.exception;

import com.employee.management.*;
import com.employee.management.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessForbiddenException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessForbiddenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),ex.getStatus(),ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnAuthorizedUserException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUnauthorizedUserRoleException(UnAuthorizedUserException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),ex.getStatus(),ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),ex.getStatus(),ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRoleException(InvalidRoleException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),ex.getStatus(),ex.getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
