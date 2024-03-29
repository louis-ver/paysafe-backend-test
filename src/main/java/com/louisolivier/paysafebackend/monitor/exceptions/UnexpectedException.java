package com.louisolivier.paysafebackend.monitor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnexpectedException extends RuntimeException {
  public UnexpectedException(String msg) {
    super(msg);
  }
}
