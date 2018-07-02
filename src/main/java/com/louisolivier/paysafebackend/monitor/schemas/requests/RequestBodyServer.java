package com.louisolivier.paysafebackend.monitor.schemas.requests;

import javax.validation.constraints.*;

public class RequestBodyServer {

  public static final Integer DEFAULT_INTERVAL = 10;
  @NotNull(message = "`url` is a required field.")
  @NotEmpty(message = "`url` cannot be blank.")
  public String url;
  @Min(1)
  @Max(100000)
  public Integer interval = DEFAULT_INTERVAL;

  public RequestBodyServer() {}
  public RequestBodyServer(String url, Integer interval) {
    this.url = url;
    this.interval = interval;
  }
}
