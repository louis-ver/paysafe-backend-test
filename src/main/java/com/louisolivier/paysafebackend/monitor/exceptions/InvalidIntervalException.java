package com.louisolivier.paysafebackend.monitor.exceptions;

public class InvalidIntervalException extends RuntimeException {
  public InvalidIntervalException(String constraint, long interval) {
    super(String.format("Interval must be %s than %s", constraint, interval));
  }
}
