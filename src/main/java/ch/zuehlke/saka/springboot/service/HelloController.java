package ch.zuehlke.saka.springboot.service;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getHello() {
		return "Hello dear Sir.";
	}
}
