package com.louisolivier.paysafebackend.monitor.exceptions;

public class NoStatusForServerException extends BadRequestException {
  public NoStatusForServerException(String msg) {
    super(msg);
  }
}
