package com.louisolivier.paysafebackend.monitor.exceptions;

public class NoDowntimeException extends BadRequestException {
  public NoDowntimeException(String msg) {
    super(msg);
  }
}
