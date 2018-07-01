package com.louisolivier.paysafebackend.monitor;

import com.louisolivier.paysafebackend.monitor.exceptions.InvalidURLException;
import com.louisolivier.paysafebackend.monitor.requests.Url;
import com.louisolivier.paysafebackend.monitor.responses.MonitorResponse;
import com.louisolivier.paysafebackend.monitor.responses.UptimeReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MonitorController {
  @PostMapping("/start")
  public MonitorResponse start(@RequestBody Server srv) {
    Server server = MonitoringService.getInstance().startMonitoring(srv.getUrl(), srv.getInterval());
    return new MonitorResponse(server.getUrl(), MonitorStatus.MONITORING);
  }

  @PostMapping("/stop")
  public MonitorResponse stop(@RequestBody Url url) {
    Server server = MonitoringService.getInstance().stopMonitoring(url.url);
    return new MonitorResponse(server.getUrl(), MonitorStatus.STOPPED);
  }

  @GetMapping("/uptime")
  public UptimeReport uptime(@RequestParam(value = "url") String url) {
    Server server = MonitoringService.getInstance().getServerByURL(url);
    return new UptimeReport(server.latestStatus(), server.getStatuses(),server.lastDowntime() != null);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ RuntimeException.class })
  @ResponseBody UnmarshalError
  handleBadRequest(HttpServletRequest req, RuntimeException ex) {
    return new UnmarshalError(req.getRequestURL().toString(), ex);
  }
}