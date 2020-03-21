package com.nin.springsecurity.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nin.springsecurity.user.User;

@Component
public class JwtTokenAuthServerProvider {
    public User getUserFromJWT(String token, String athenticationServer) {
    	 String uri = athenticationServer + "/auth/client/validate?token="+token;
    	 RestTemplate restTemplate = new RestTemplate();
    	 ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
    	 if (response != null && response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node;
				try {
					node = mapper.readTree(response.getBody());
					String username = node.path("username").asText();
					Long userId= node.path("id").asLong();
					User user = new User();
					user.setUsername(username);
					user.setId(userId);
					return user;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				
    	 }
        return null;
    }
}
