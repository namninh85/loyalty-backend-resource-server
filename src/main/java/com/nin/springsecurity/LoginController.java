package com.nin.springsecurity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nin.springsecurity.payload.LoginRequest;
import com.nin.springsecurity.payload.LoginResponse;
import com.nin.springsecurity.user.User;
import com.nin.springsecurity.user.UserRepository;

@RestController
@RequestMapping("/auth")
public class LoginController {
	int LOGIN_FAIL = -1;
	int LOGIN_SUCCESSFUL = 0;
	int LOGIN_NEW = 1;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	LodaRestController lodaRestController;
	
	@PostMapping("/login-create")
	ResponseEntity<Map<String, Object>> clientLoginCreate(@Valid @RequestBody LoginRequest loginRequest) {

		User user = userRepository.findByUsername(loginRequest.getUsername());
		int isCreateNew = 0;
		if (user == null) {
			User newUser = new User();
			newUser.setUsername(loginRequest.getUsername());
			newUser.setPassword(loginRequest.getUsername());
			newUser.setCreated(null);
			userRepository.save(newUser);
			isCreateNew = 1;
		}

		LoginResponse loginResponse = lodaRestController.authenticateUser(loginRequest);

		int error = (isCreateNew == 1) ? LOGIN_NEW : LOGIN_SUCCESSFUL;
		Map<String, Object> data = new HashMap<String, Object>() {
			{
				put("token", loginResponse.getAccessToken());
			}
		};
		Map<String, Object> out = new HashMap<String, Object>() {
			{
				put("error", error);
				put("data", data);
			}
		};
		return new ResponseEntity<>(out, HttpStatus.OK);

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void logout(HttpServletRequest request) {

	}

}
