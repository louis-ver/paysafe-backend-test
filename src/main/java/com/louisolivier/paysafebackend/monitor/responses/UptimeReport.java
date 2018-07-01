package com.louisolivier.paysafebackend.monitor.responses;

import com.louisolivier.paysafebackend.monitor.ServerStatus;

import java.util.Date;
import java.util.Stack;

public class UptimeReport {
  public ServerStatus currentStatus;
  public Stack<ServerStatus> allStatuses;
  private boolean hasBeenDown;

  public UptimeReport(ServerStatus currentStatus, Stack<ServerStatus> allStatuses, boolean hasBeenDown) {
    this.currentStatus = currentStatus;
    this.allStatuses = allStatuses;
    this.hasBeenDown = hasBeenDown;
  }

  public Long getTimeSinceLastDowntime() {
    if (hasBeenDown) {
      return new Date().getTime() - currentStatus.getTime();
    }
    else {
      return null;
    }
  }
}
