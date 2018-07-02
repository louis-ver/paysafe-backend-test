package com.louisolivier.paysafebackend.monitor;

import com.louisolivier.paysafebackend.monitor.exceptions.BadRequestException;
import com.louisolivier.paysafebackend.monitor.requests.RequestBodyServer;
import com.louisolivier.paysafebackend.monitor.requests.RequestBodyURL;
import com.louisolivier.paysafebackend.monitor.responses.MonitorResponse;
import com.louisolivier.paysafebackend.monitor.responses.UptimeReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.ws.http.HTTPBinding;

@RestController
public class MonitorController {
  @PostMapping("/start")
  @ResponseStatus(value = HttpStatus.CREATED)
  public MonitorResponse start(@Valid @RequestBody RequestBodyServer srv, HttpServletRequest req, HttpServletResponse resp) {
    Server server = MonitoringService.getInstance().startMonitoring(srv.url, srv.interval);
    resp.setHeader("Location", "/uptime?" + srv.url);
    return new MonitorResponse(server.getUrl(), MonitorStatus.MONITORING);
  }

  @PostMapping("/stop")
  public MonitorResponse stop(@Valid @RequestBody RequestBodyURL url) {
    Server server = MonitoringService.getInstance().stopMonitoring(url.url);
    return new MonitorResponse(server.getUrl(), MonitorStatus.STOPPED);
  }

  @GetMapping("/uptime")
  public UptimeReport uptime(@RequestParam(value = "url") String url) {
    Server server = MonitoringService.getInstance().getServerByURL(url);
    return new UptimeReport(server.latestStatus(), server.getStatuses(),server.lastDowntime() != null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiError handleUnmarshalError(MethodArgumentNotValidException exception, HttpServletRequest req) {
    return ApiError.fromBindingErrors(exception.getBindingResult(), HttpStatus.BAD_REQUEST, req.getRequestURI());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiError handleInvalidGet(MissingServletRequestParameterException exception, HttpServletRequest req) {
    return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), req.getRequestURI());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiError handleInvalidPost(BadRequestException exception, HttpServletRequest req) {
    return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), req.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus()
  public ApiError handleUnexpectedErrors(Exception exception, HttpServletRequest req) {
    return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), req.getRequestURI());
  }
}