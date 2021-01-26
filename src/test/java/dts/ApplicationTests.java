package dts;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	
	private int port;
	private String url;
	private RestTemplate restTemplate;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + port + "/dts";
//		System.err.println(this.url);
		
		this.restTemplate = new RestTemplate();
	}

	@AfterEach
	// make sure each test cleans up any data it generates
	public void tearDown (){
		this.restTemplate
			.delete(this.url);
	}
	
	@Test
	public void testServerIsUp() {
		
	}
	
	

}
 