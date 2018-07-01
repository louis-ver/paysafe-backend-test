package com.louisolivier.paysafebackend.monitor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidURLException extends RuntimeException {
  public InvalidURLException(String url) {
    super(String.format("'%s' is not a valid URL. Please provide a valid URL.", url));
  }
}
