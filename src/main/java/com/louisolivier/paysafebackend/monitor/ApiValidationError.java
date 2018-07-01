package com.louisolivier.paysafebackend.monitor;

public class ApiValidationError {
  private String object;
  private String message;

  ApiValidationError(String object, String message) {
    this.object = object;
    this.message = message;
  }
}
