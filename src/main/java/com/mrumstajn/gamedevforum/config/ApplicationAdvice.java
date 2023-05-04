package com.mrumstajn.gamedevforum.config;

import com.mrumstajn.gamedevforum.common.response.ErrorCode;
import com.mrumstajn.gamedevforum.common.response.ErrorResponse;
import com.mrumstajn.gamedevforum.exception.CannotTargetSelfException;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.exception.LoginException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage(), ErrorCode.RESOURCE_NOT_FOUND));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage(), ErrorCode.INVALID_CREDENTIALS));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateReactionException(DuplicateResourceException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage(), ErrorCode.DUPLICATE_RESOURCE));
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedActionException(UnauthorizedActionException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage(), ErrorCode.UNAUTHORIZED));
    }

    @ExceptionHandler(CannotTargetSelfException.class)
    public ResponseEntity<ErrorResponse> handelCannotTargetSelfException(CannotTargetSelfException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage(), ErrorCode.CANNOT_TARGET_SELF));
    }
}
