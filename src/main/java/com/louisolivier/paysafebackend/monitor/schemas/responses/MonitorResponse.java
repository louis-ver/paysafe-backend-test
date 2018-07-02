package com.louisolivier.paysafebackend.monitor.schemas.responses;

import com.louisolivier.paysafebackend.monitor.MonitorStatus;

public class MonitorResponse {
  public String hostname;
  public MonitorStatus status;

  public MonitorResponse() {}
  public MonitorResponse(String hostname, MonitorStatus status) {
    this.hostname = hostname;
    this.status = status;
  }
}
