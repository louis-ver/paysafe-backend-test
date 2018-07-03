package com.louisolivier.paysafebackend.monitor.schemas.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApiError {

  private Date timestamp;
  private HttpStatus status;
  private String message;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<String> errors = new ArrayList<>();
  private String path;

  public ApiError(HttpStatus status, String message, String path) {
    this.timestamp = new Date();
    this.status = status;
    this.message = message;
    this.path = path;
  }

  public void addValidationError(String error) {
    errors.add(error);
  }

  public List<String> getErrors() {
    return errors;
  }

  public String getError() { return status.getReasonPhrase(); }

  public String getMessage() { return message; }

  public Integer getStatus() { return status.value(); }

  public String getPath() { return path; }

  public Date getTimestamp() { return timestamp; }

  public static ApiError fromBindingErrors(Errors errors, HttpStatus status, String path) {
    ApiError error = new ApiError(status, "Validation failed. " + errors.getErrorCount() + " error(s)", path);
    for (ObjectError objectError : errors.getAllErrors()) {
      error.addValidationError(objectError.getDefaultMessage());
    }
    return error;
  }
}
