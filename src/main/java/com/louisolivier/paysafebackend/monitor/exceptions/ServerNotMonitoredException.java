package com.louisolivier.paysafebackend.monitor.exceptions;

public class ServerNotMonitoredException extends RuntimeException {
  public ServerNotMonitoredException(String msg) {
    super(msg);
  }
}
