package ch.zuehlke.saka.springboot.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private URI base;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() {
		base = URI.create("http://localhost:" + port + "/hello");
	}

	@Test
	public void getHello() {
		ResponseEntity<HelloTO> response = template.getForEntity(base, HelloTO.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(response.getBody().getHelloText()).isEqualTo("Hello dear Sir.");
	}
}