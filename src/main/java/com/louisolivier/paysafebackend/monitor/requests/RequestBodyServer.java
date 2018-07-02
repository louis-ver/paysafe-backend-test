package com.louisolivier.paysafebackend.monitor.requests;

import javax.validation.constraints.*;

public class RequestBodyServer {
  @NotNull(message = "`url` is a required field.")
  public String url;
  @NotNull(message = "`interval` is a required field.")
  @Min(1)
  @Max(100000)

  public Integer interval;

  public RequestBodyServer() {}
  public RequestBodyServer(String url, Integer interval) {
    this.url = url;
    this.interval = interval;
  }
}
