package com.github.angel.raa.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundUsername extends RuntimeException {
    private boolean error;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public NotFoundUsername(String message, boolean error, HttpStatus status, LocalDateTime timestamp) {
        super(message);
        this.error = error;
        this.status = status;
        this.timestamp = timestamp;
    }
}
