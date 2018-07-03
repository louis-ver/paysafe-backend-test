package com.louisolivier.paysafebackend.monitor.dto.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class URLDto {
  @NotNull(message = "`url` is a required field.")
  @NotEmpty(message = "`url` cannot be blank.")
  public String url;
}
