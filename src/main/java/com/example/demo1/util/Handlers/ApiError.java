package com.example.demo1.util.Handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime time;

    private String message;

    private List<String> errors;

    public ApiError() {
        this.time = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable e) {
        this();
        this.status = status;
        this.message = "Unexpected error: " + e.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable e) {
        this();
        this.status = status;
        this.message = message;
        //this.debug = e.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, String error) {
        this();
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
    }

    ApiError(HttpStatus status, String message, List<String> error) {
        this();
        this.status = status;
        this.message = message;
        this.errors = error;
    }
}
