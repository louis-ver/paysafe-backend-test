package com.louisolivier.paysafebackend.monitor;

import org.springframework.http.converter.HttpMessageNotReadableException;

public class UnmarshalError {
  public final String url;
  public final String error;

  public UnmarshalError(String url, RuntimeException e) {
    this.url = url;
    this.error = e.getMessage();
  }
}
