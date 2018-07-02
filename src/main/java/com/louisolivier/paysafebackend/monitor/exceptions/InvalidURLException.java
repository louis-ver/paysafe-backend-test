package com.louisolivier.paysafebackend.monitor.exceptions;

public class InvalidURLException extends BadRequestException {
  public InvalidURLException(String url) {
    super(String.format("'%s' is not a valid URL. Please provide a valid URL.", url));
  }
}
