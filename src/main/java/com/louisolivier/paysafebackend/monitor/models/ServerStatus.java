package com.louisolivier.paysafebackend.monitor.models;

import java.util.Date;

public class ServerStatus {
  public enum Status {
    UP,
    DOWN
  }
  private Status status;
  private Date time;

  public ServerStatus(Status status, Date time) {
    this.status = status;
    this.time = time;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Long getTime() {
    return time.getTime();
  }

  public void setTime(Date time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return status.equals(Status.UP) ? "up" : "down";
  }
}
