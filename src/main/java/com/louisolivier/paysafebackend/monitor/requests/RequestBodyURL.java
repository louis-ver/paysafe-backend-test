package com.louisolivier.paysafebackend.monitor.requests;

import javax.validation.constraints.NotNull;

public class RequestBodyURL {
  @NotNull(message = "`url` is a required field.")
  public String url;
}
