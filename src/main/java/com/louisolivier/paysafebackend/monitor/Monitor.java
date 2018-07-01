package com.louisolivier.paysafebackend.monitor;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Monitor {
  public String url;
  public Integer interval;
  public MonitorStatus status;

  public Monitor() {}

  public Monitor(String url, Integer interval, MonitorStatus status) {
    this.url = url;
    this.interval = interval;
    this.status = status;
  }
}
