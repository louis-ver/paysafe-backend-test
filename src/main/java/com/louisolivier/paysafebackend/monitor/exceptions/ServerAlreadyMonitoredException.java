package com.louisolivier.paysafebackend.monitor.exceptions;

public class ServerAlreadyMonitoredException extends RuntimeException {
  public ServerAlreadyMonitoredException(String msg) {
    super(msg);
  }
}
