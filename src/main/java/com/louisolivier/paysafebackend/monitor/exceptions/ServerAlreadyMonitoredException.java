package com.louisolivier.paysafebackend.monitor.exceptions;

public class ServerAlreadyMonitoredException extends BadRequestException {
  public ServerAlreadyMonitoredException(String msg) {
    super(msg);
  }
}
