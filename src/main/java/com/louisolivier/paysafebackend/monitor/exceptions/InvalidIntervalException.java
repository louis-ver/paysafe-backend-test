package com.louisolivier.paysafebackend.monitor.exceptions;

public class InvalidIntervalException extends BadRequestException {
  public InvalidIntervalException(String constraint, long interval) {
    super(String.format("Interval must be %s than %s", constraint, interval));
  }
  public InvalidIntervalException(String msg) {
    super(msg);
  }
}
