package com.louisolivier.paysafebackend.monitor;

import com.louisolivier.paysafebackend.monitor.exceptions.BadRequestException;
import com.louisolivier.paysafebackend.monitor.models.MonitorStatus;
import com.louisolivier.paysafebackend.monitor.models.Server;
import com.louisolivier.paysafebackend.monitor.schemas.requests.PayloadServer;
import com.louisolivier.paysafebackend.monitor.schemas.requests.PayloadUrl;
import com.louisolivier.paysafebackend.monitor.schemas.responses.ApiError;
import com.louisolivier.paysafebackend.monitor.schemas.responses.MonitorResponse;
import com.louisolivier.paysafebackend.monitor.schemas.responses.UptimeReport;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class MonitorController {
  @PostMapping("/start")
  @ResponseStatus(value = HttpStatus.CREATED)
  public MonitorResponse start(@Valid @RequestBody PayloadServer srv, HttpServletRequest req, HttpServletResponse resp) {
    Server server = MonitoringService.getInstance().startMonitoring(srv.url, srv.interval);
    resp.setHeader("Location", "/uptime?" + srv.url);
    return new MonitorResponse(server.getUrl(), MonitorStatus.MONITORING);
  }

  @PatchMapping("/start")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void start(@Valid @RequestBody PayloadServer srv) {
    MonitoringService.getInstance().changeMonitoring(srv.url, srv.interval);
  }

  @PostMapping("/stop")
  public MonitorResponse stop(@Valid @RequestBody PayloadUrl url) {
    Server server = MonitoringService.getInstance().stopMonitoring(url.url);
    return new MonitorResponse(server.getUrl(), MonitorStatus.STOPPED);
  }

  @GetMapping("/uptime")
  public UptimeReport uptime(@RequestParam(value = "url") String url) {
    Server server = MonitoringService.getInstance().getServerByUrl(url);
    return new UptimeReport(server);
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
}