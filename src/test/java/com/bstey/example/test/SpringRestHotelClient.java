package com.bstey.example.test;

import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class SpringRestHotelClient {

	public static final String REST_SERVICE_URI = "http://localhost:8090/example/v1";

	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * user-credentials.
	 */
	private static HttpHeaders getHeaders() {
		String plainCredentials = "toto:titi";
		String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	/*
	 * Send a GET request to get list of all users.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void listAllUsers() {
		System.out.println("\nTesting listAllHotels API-----------");
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI + "/hotels/", HttpMethod.GET, request,
				String.class);
		System.out.println(response.getBody());
	}

}
