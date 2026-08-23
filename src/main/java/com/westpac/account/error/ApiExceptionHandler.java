package com.westpac.account.error;

import com.westpac.account.api.model.ApiError;
import com.westpac.account.exception.SavingsAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.OffsetDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(ApiExceptionHandler.class);


    /**
     * Handles exceptions thrown by the savings account service.
     * @param exception the exception
     * @return the HTTP response entity
     */
    @ExceptionHandler(SavingsAccountException.class)
    public ResponseEntity<ApiError> handleSavingsAccountException(
            SavingsAccountException exception) {

        HttpStatus status = switch (exception.getCode()) {
            case "ACCOUNT_NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "ACCOUNT_LIMIT_EXCEEDED" -> HttpStatus.CONFLICT;
            case "OFFENSIVE_NICKNAME" -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.BAD_REQUEST;
        };

        log.warn(
                "Savings account request rejected, code={}",
                exception.getCode()
        );

        return ResponseEntity.status(status)
                .body(new ApiError(
                        exception.getCode(),
                        exception.getMessage(),
                        OffsetDateTime.now()
                ));
    }

    /**
     * Handles validation exceptions thrown by Spring MVC.
     * @param exception the exception
     * @return the HTTP response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid request");

        return ResponseEntity.badRequest()
                .body(new ApiError(
                        "VALIDATION_ERROR",
                        message,
                        OffsetDateTime.now()
                ));
    }

    /**
     * Handles database exceptions thrown by Spring Data.
     * @param exception the exception
     * @return the HTTP response entity
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDatabaseException(
            DataAccessException exception) {

        log.error("Database operation failed", exception);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ApiError(
                        "SERVICE_UNAVAILABLE",
                        "Service temporarily unavailable",
                        OffsetDateTime.now()
                ));
    }
}
