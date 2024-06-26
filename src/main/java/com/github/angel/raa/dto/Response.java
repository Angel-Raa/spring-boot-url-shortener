package com.github.angel.raa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
@Data
public class Response<T> implements Serializable {
    @Serial
    @JsonIgnore
    private static final long serialVersionUID = 10_028_102_1928_192_028_9L;
    private transient T data;
    private String message;
    private HttpStatus status;
    private int code;
    private boolean error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

}
