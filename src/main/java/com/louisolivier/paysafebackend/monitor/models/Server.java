package com.louisolivier.paysafebackend.monitor.models;

import com.louisolivier.paysafebackend.monitor.exceptions.*;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
  private URL url;
  private Integer interval;
  private LinkedList<ServerStatus> statuses = new LinkedList<>();
  private ScheduledExecutorService executor;
  private final Runnable healthCheck = () -> {
    try (Socket s = new Socket(url.getHost(), 80)) {
      System.out.println(String.format("url: %s, status: up", url));
      statuses.add(new ServerStatus(ServerStatus.Status.UP, new Date()));
    } catch (IOException e) {
      statuses.add(new ServerStatus(ServerStatus.Status.DOWN, new Date()));
      System.out.println(String.format("url: %s, status: down", url));
    }
  };

  public Server() {}

  public Server(String url, Integer interval) {
    setUrl(url);
    setInterval(interval);
  }

  public String getUrl() {
    return this.url.toString();
  }

  public String getHost() {
    return this.url.getHost();
  }

  public void setUrl(String url) {
    try {
      this.url = new URL(url);
    } catch (MalformedURLException e) {
      throw new InvalidURLException(url);
    }
  }

  public Integer getInterval() {
    return this.interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  public LinkedList<ServerStatus> getStatuses() {
    return statuses;
  }

  public void startPing() {
    stopPing();
    this.executor = Executors.newScheduledThreadPool(1);
    this.executor.scheduleAtFixedRate(this.healthCheck, 0, this.interval, TimeUnit.SECONDS);
  }

  public void stopPing() {
    if (this.executor != null) {
      this.executor.shutdown();
    }
  }

  public ServerStatus latestStatus() {
    if (this.statuses.isEmpty()) {
      throw new NoStatusForServerException(String.format("No status information for '%s'.", this.url));
    }
    return this.statuses.getLast();
  }

  public Long getTimeSinceStatusChange() {
    for (ServerStatus status: this.statuses) {
      if (status.getStatus() != latestStatus().getStatus()) {
        return new Date().getTime() - latestStatus().getTime();
      }
    }
    return null;
  }

  public static String getHostnameFromURL(String url) {
    try {
      String hostname = new URL(url).getHost();
      return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
    }
    catch (MalformedURLException e) {
      throw new InvalidURLException(url);
    }
  }

  public String monitorSummary() {
    Long timeSinceStatusChange = getTimeSinceStatusChange();
    String timeFormat = "%d min, %d sec";
    String changeSince;
    if (timeSinceStatusChange != null) {
      changeSince = timeFormatter(timeFormat, timeSinceStatusChange);
    }
    else {
      Long timeSinceMonitoringStart = new Date().getTime() - statuses.getFirst().getTime();
      changeSince = timeFormatter(timeFormat, timeSinceMonitoringStart)  + " (since monitoring started)";
    }
    return String.format("%s is currently %s. It has been %s for %s.", getHost(), latestStatus(), latestStatus(), changeSince);
  }

  private String timeFormatter(String format, Long time) {
    return String.format(
            format,
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes(time))));
  }
}