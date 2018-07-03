package com.louisolivier.paysafebackend.monitor.dto.responses;

import com.louisolivier.paysafebackend.monitor.models.MonitorStatus;

public class MonitorDto {
  public String hostname;
  public MonitorStatus status;

  public MonitorDto() {}
  public MonitorDto(String hostname, MonitorStatus status) {
    this.hostname = hostname;
    this.status = status;
  }
}
