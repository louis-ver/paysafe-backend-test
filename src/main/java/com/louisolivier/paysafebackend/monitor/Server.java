package com.louisolivier.paysafebackend.monitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.louisolivier.paysafebackend.monitor.exceptions.*;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.louisolivier.paysafebackend.monitor.constants.Constants.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Server {
  private URL url;
  private Integer interval;
  private Stack<ServerStatus> statuses = new Stack<>();
  private ScheduledExecutorService executor;
  private final Runnable healthCheck = () -> {
    try (Socket s = new Socket(url.getHost(), 80)) {
      System.out.println(String.format("url: %s, status: up", url));
      statuses.push(new ServerStatus(ServerStatus.Status.UP, new Date()));
    } catch (IOException e) {
      statuses.push(new ServerStatus(ServerStatus.Status.DOWN, new Date()));
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

  String getHost() {
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
    if (interval < MINIMUM_INTERVAL) {
      throw new InvalidIntervalException("greater", MINIMUM_INTERVAL);
    }
    else if (interval > MAXIMUM_INTERVAL) {
      throw new InvalidIntervalException("smaller", MAXIMUM_INTERVAL);
    }
    this.interval = interval;
  }

  public Stack<ServerStatus> getStatuses() {
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
    if (this.statuses.empty()) {
      throw new NoStatusForServerException(String.format("No status information for '%s'.", this.url));
    }
    return this.statuses.peek();
  }

  public Long lastDowntime() {
    for (ServerStatus status: this.statuses) {
      if (status.getStatus().equals(ServerStatus.Status.DOWN)) {
        return status.getTime();
      }
    }
    return null;
  }
}