package com.louisolivier.paysafebackend.monitor.exceptions;

public class NoDowntimeException extends RuntimeException {
  public NoDowntimeException(String msg) {
    super(msg);
  }
}
