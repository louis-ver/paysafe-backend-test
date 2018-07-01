package com.louisolivier.paysafebackend.monitor.exceptions;

public class UnexpectedException extends RuntimeException {
  public UnexpectedException(String msg) {
    super(msg);
  }
}
