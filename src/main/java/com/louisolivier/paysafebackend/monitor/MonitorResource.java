package com.louisolivier.paysafebackend.monitor;

import com.louisolivier.paysafebackend.monitor.exceptions.BadRequestException;
import com.louisolivier.paysafebackend.monitor.models.MonitorStatus;
import com.louisolivier.paysafebackend.monitor.models.Server;
import com.louisolivier.paysafebackend.monitor.dto.requests.ServerDto;
import com.louisolivier.paysafebackend.monitor.dto.requests.URLDto;
import com.louisolivier.paysafebackend.monitor.dto.responses.ApiError;
import com.louisolivier.paysafebackend.monitor.dto.responses.MonitorDto;
import com.louisolivier.paysafebackend.monitor.dto.responses.ReportDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class MonitorResource {
  @PostMapping("/start")
  @ResponseStatus(value = HttpStatus.CREATED)
  public MonitorDto start(@Valid @RequestBody ServerDto srv, HttpServletRequest req, HttpServletResponse resp) {
    Server server = MonitoringService.getInstance().startMonitoring(srv.url, srv.interval);
    resp.setHeader("Location", "/report?" + srv.url);
    return new MonitorDto(server.getUrl(), MonitorStatus.MONITORING);
  }

  @PatchMapping("/start")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void start(@Valid @RequestBody ServerDto srv) {
    MonitoringService.getInstance().changeMonitoring(srv.url, srv.interval);
  }

  @PostMapping("/stop")
  public MonitorDto stop(@Valid @RequestBody URLDto url) {
    Server server = MonitoringService.getInstance().stopMonitoring(url.url);
    return new MonitorDto(server.getUrl(), MonitorStatus.STOPPED);
  }

  @GetMapping("/report")
  public ReportDto uptime(@RequestParam(value = "url") String url) {
    Server server = MonitoringService.getInstance().getServerByUrl(url);
    return new ReportDto(server);
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