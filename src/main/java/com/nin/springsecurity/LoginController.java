package com.nin.springsecurity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nin.springsecurity.payload.LoginRequest;
import com.nin.springsecurity.payload.LoginResponse;
import com.nin.springsecurity.user.AccessToken;
import com.nin.springsecurity.user.AccessTokenRepository;
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
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Value("${resetPasswordToken}")
    private String resetPasswordToken;
	
	@PostMapping("/login-create")
	ResponseEntity<Map<String, Object>> clientLoginCreate(@Valid @RequestBody LoginRequest loginRequest) {

		User user = userRepository.findByUsername(loginRequest.getUsername());
		int isCreateNew = 0;
		if (user == null) {
			User newUser = new User();
			newUser.setUsername(loginRequest.getUsername());
			newUser.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
			newUser.setCreated(null);
			userRepository.save(newUser);
			isCreateNew = 1;
		}
		try {
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
		catch (Exception e) {
			Map<String, Object> data = new HashMap<String, Object>() {
				{
					put("message", "Login Fail");
				}
			};
			Map<String, Object> out = new HashMap<String, Object>() {
				{
					put("error", LOGIN_FAIL);
					put("data", data);
				}
			};
			return new ResponseEntity<>(out, HttpStatus.UNAUTHORIZED);
		}

	}

	
	@PostMapping("/resetpassword")
	ResponseEntity<Map<String, Object>> resetPassword(HttpServletRequest httpServletRequest, @Valid @RequestBody LoginRequest loginRequest) {

		try {
			User user = userRepository.findByUsername(loginRequest.getUsername());
			String token = getJwtFromRequest(httpServletRequest);
			if(!resetPasswordToken.equalsIgnoreCase(token)) {
				throw new Exception("Token is not matched");
			}
			if (user == null) {
				throw new Exception("User not found!");
			}
			user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
			userRepository.save(user);
			
			Map<String, Object> out = new HashMap<String, Object>() {
				{
					put("error", 0);
					put("data", null);
				}
			};
			return new ResponseEntity<>(out, HttpStatus.OK);
		}
		catch (Exception e) {
			Map<String, Object> data = new HashMap<String, Object>() {
				{
					put("message", e.getMessage());
				}
			};
			Map<String, Object> out = new HashMap<String, Object>() {
				{
					put("error", LOGIN_FAIL);
					put("data", data);
				}
			};
			return new ResponseEntity<>(out, HttpStatus.UNAUTHORIZED);
		}

	}
	
	
	
	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
