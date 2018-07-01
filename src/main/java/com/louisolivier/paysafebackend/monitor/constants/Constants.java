package com.louisolivier.paysafebackend.monitor.constants;

public final class Constants {
  private Constants() { }

  public static final int DEFAULT_INTERVAL = 10;
  public static final int MINIMUM_INTERVAL = 1;
  public static final int MAXIMUM_INTERVAL = 1000000;

  public static final String DEFAULT_SERVER = "https://api.test.paysafe.com/accountmanagement/monitor";
}
