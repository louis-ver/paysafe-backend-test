package com.louisolivier.paysafebackend.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.louisolivier.paysafebackend.monitor.MonitoringService;
import com.louisolivier.paysafebackend.monitor.dto.requests.ServerDto;
import com.louisolivier.paysafebackend.monitor.dto.requests.URLDto;
import com.louisolivier.paysafebackend.monitor.dto.responses.MonitorDto;
import com.louisolivier.paysafebackend.monitor.dto.responses.ReportDto;
import com.louisolivier.paysafebackend.monitor.models.Server;
import com.louisolivier.paysafebackend.monitor.models.ServerStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.LinkedList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MonitorResourceTest {
  @Autowired
  private TestRestTemplate testRestTemplate;

  private final static String MONITORED_SERVER = "http://test-server.com";

  @Before
  public void init() {
    Server server = new Server();
    server.setUrl(MONITORED_SERVER);
    server.setInterval(1);
    server.setHealthCheck(() -> System.out.println("test-monitoring"));
    LinkedList<ServerStatus> statuses = new LinkedList<>();
    statuses.add(new ServerStatus(ServerStatus.Status.UP, new Date()));
    server.setStatuses(statuses);
    MonitoringService ms = MonitoringService.getInstance();
    ms.addServer(server);

  }

  @Test
  public void it_should_return_created_status_with_new_url() {
    ServerDto server = new ServerDto("http://new-server.com", 2);
    ResponseEntity<MonitorDto> response = testRestTemplate.postForEntity("/start", server, MonitorDto.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  public void it_should_return_ok_for_stopped_monitoring() {
    URLDto url = new URLDto();
    url.url = MONITORED_SERVER;
    ResponseEntity<MonitorDto> response = testRestTemplate.postForEntity("/stop", url, MonitorDto.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void it_should_return_ok_for_uptime_report() {
    ResponseEntity<ReportDto> response = testRestTemplate.getForEntity("/report?url=" + MONITORED_SERVER, ReportDto.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
