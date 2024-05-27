package com.github.angel.raa.exception;

import com.github.angel.raa.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handlerConstrainViolationException(@NotNull ConstraintViolationException exception) {
        List<String> error = exception.getConstraintViolations()
                .stream()
                .map(v -> String.format("%s: %s", v.getPropertyPath(), v.getMessage()))
                .toList();
        String message = "Validation failed for the following fields:\n" + String.join("\n", error);
        ErrorResponse response = new ErrorResponse();
        response.setCode(400);
        response.setMessage(message);
        response.setTimestamp(now());
        response.setError(true);
        response.setStatus(BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundUsername.class)
    public ResponseEntity<ErrorResponse> notFoundUsername (@NotNull NotFoundUsername notFound){
        ErrorResponse response = new ErrorResponse(notFound.getMessage(), notFound.getStatus(),404, true, notFound.getTimestamp());
       return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<ErrorResponse> shortUrlNotFoundException(@NotNull ShortUrlNotFoundException urlNotFoundException){
        ErrorResponse response = new ErrorResponse(urlNotFoundException.getMessage(), HttpStatus.NOT_FOUND,404, true,now());
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> invalidTokenException(@NotNull InvalidTokenException tokenException){
        ErrorResponse response = new ErrorResponse(tokenException.getMessage(), tokenException.getStatus(),404, true, tokenException.getTimestamp());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> invalidPasswordException(@NotNull InvalidPasswordException invalidPasswordException){
        ErrorResponse response = new ErrorResponse(invalidPasswordException.getMessage(), BAD_REQUEST,404, true, now());
        return ResponseEntity.status(response.getStatus()).body(response);
    }



}
