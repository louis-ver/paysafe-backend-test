package com.louisolivier.paysafebackend.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
  private HttpStatus status;
  private LocalDateTime timestamp;
  private String message;
  private String debugMessage;

  private ApiError() {
    timestamp = LocalDateTime.now();
  }

  ApiError(HttpStatus status) {
    this(status, null, null);
  }

  ApiError(HttpStatus status, Throwable ex) {
    this(status, "Unexpected error", ex);
  }

  ApiError(HttpStatus status, String message, Throwable ex) {
    this.status = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }
}
