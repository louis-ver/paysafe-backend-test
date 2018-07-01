package com.louisolivier.paysafebackend.monitor.exceptions;

public class NoStatusForServerException extends RuntimeException {
  public NoStatusForServerException(String msg) {
    super(msg);
  }
}
