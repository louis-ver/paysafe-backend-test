package com.louisolivier.paysafebackend.monitor;

import com.louisolivier.paysafebackend.monitor.exceptions.ServerNotMonitoredException;
import com.louisolivier.paysafebackend.monitor.models.Server;

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
    String hostname = Server.getHostnameFromURL(url);
    Server server = this.servers.get(hostname);
    if (server == null) {
      Server newServer = new Server(url, interval);
      newServer.startPing();
      this.servers.put(hostname, newServer);
      return newServer;
    } else {
      server.setInterval(interval);
      server.startPing();
      return server;
    }
  }

  synchronized void changeMonitoring(String url, Integer interval) {
    String hostname = Server.getHostnameFromURL(url);
    Server server = this.servers.get(hostname);
    if (server == null) {
      throw new ServerNotMonitoredException(String.format("Server at '%s' is not currently being monitored.", url));
    }
    else {
      server.setInterval(interval);
      server.startPing();
      this.servers.put(hostname, server);
    }
  }

  synchronized Server stopMonitoring(String url) {
    Server server = getServerByUrl(url);
    server.stopPing();
    return this.servers.remove(Server.getHostnameFromURL(url));
  }

  public Server getServerByUrl(String url) {
    Server server = servers.get(Server.getHostnameFromURL(url));
    if (server == null) {
      throw new ServerNotMonitoredException(String.format("Server at '%s' is not currently being monitored.", url));
    }
    return server;
  }
}
