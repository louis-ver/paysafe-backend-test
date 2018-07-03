package com.louisolivier.paysafebackend.monitor.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.louisolivier.paysafebackend.monitor.models.Server;
import com.louisolivier.paysafebackend.monitor.models.ServerStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDto {
  public String serverName;
  public String summary;
  public ServerStatus currentStatus;
  public List<ServerStatus> allStatuses;

  public ReportDto() {}

  public ReportDto(Server server) {
    this.serverName = server.getHost();
    this.currentStatus = server.latestStatus();
    this.allStatuses = server.getStatuses();
    this.summary = server.monitorSummary();
  }
}
