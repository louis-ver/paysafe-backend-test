package com.louisolivier.paysafebackend.monitor.schemas.responses;

import com.louisolivier.paysafebackend.monitor.models.Server;
import com.louisolivier.paysafebackend.monitor.models.ServerStatus;

import java.util.List;

public class UptimeReport {
  public String serverName;
  public String summary;
  public ServerStatus currentStatus;
  public List<ServerStatus> allStatuses;

  public UptimeReport(Server server) {
    this.serverName = server.getHost();
    this.currentStatus = server.latestStatus();
    this.allStatuses = server.getStatuses();
    this.summary = server.monitorSummary();
  }
}
