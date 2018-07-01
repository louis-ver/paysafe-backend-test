package com.louisolivier.paysafebackend.monitor;

import com.louisolivier.paysafebackend.monitor.exceptions.ServerNotMonitoredException;

import java.util.HashMap;
import java.util.Map;

public class MonitoringService {
  private Map<String, Server> servers = new HashMap<>();

  private static MonitoringService ourInstance = new MonitoringService();

  static MonitoringService getInstance() {
    return ourInstance;
  }

  private MonitoringService() {
  }

  synchronized Server startMonitoring(String url, Integer interval) {
    Server server = this.servers.get(url);
    if (server == null) {
      Server newServer = new Server(url, interval);
      newServer.startPing();
      this.servers.put(newServer.getUrl(), newServer);
      return newServer;
    } else {
      server.setInterval(interval);
      server.startPing();
      return server;
    }
  }

  synchronized Server stopMonitoring(String url) {
    Server server = this.servers.get(url);
    if (server == null) {
      throw new ServerNotMonitoredException(String.format("Server at '%s' is not currently being monitored.", url));
    }
    server.stopPing();
    return this.servers.remove(url);
  }

  public Server getServerByURL(String url) {
    Server server = servers.get(url);
    if (server == null) {
      throw new ServerNotMonitoredException(String.format("Server at '%s' is not currently being monitored.", url));
    }
    return server;
  }
}
