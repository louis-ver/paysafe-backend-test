package com.louisolivier.paysafebackend.monitor.exceptions;

public class ServerNotMonitoredException extends BadRequestException {
  public ServerNotMonitoredException(String msg) {
    super(msg);
  }
}
