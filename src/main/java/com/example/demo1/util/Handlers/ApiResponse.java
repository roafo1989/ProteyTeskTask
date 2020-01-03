package com.example.demo1.util.Handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiResponse<T> {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime time;

    private T result;

    private ApiResponse() {
        this.time = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus status, T result) {
        this();
        this.status = status;
        this.result = result;
    }
}
