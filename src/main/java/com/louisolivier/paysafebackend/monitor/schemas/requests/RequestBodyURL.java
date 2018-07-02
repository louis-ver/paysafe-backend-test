package com.louisolivier.paysafebackend.monitor.schemas.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestBodyURL {
  @NotNull(message = "`url` is a required field.")
  @NotEmpty(message = "`url` cannot be blank.")
  public String url;
}
