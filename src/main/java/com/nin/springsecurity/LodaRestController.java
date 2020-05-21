package com.nin.springsecurity;
/*******************************************************
 * For Vietnamese readers:
 *    Các bạn thân mến, mình rất vui nếu project này giúp 
 * ích được cho các bạn trong việc học tập và công việc. Nếu 
 * bạn sử dụng lại toàn bộ hoặc một phần source code xin để 
 * lại dường dẫn tới github hoặc tên tác giá.
 *    Xin cảm ơn!
 *******************************************************/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nin.springsecurity.jwt.JwtTokenProvider;
import com.nin.springsecurity.payload.LoginRequest;
import com.nin.springsecurity.payload.LoginResponse;
import com.nin.springsecurity.payload.RandomStuff;
import com.nin.springsecurity.user.AccessToken;
import com.nin.springsecurity.user.AccessTokenRepository;
import com.nin.springsecurity.user.CustomUserDetails;
import com.nin.springsecurity.user.User;
import com.nin.springsecurity.user.UserRepository;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 5/1/2019
 * Github: https://github.com/loda-kun
 */
@RestController
@RequestMapping("/auth")
public class LodaRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private AccessTokenRepository accessTokenRepository;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực thông tin người dùng Request lên
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        
        AccessToken aT = new AccessToken();
        aT.setToken(jwt);
        accessTokenRepository.save(aT);
		
        return new LoginResponse(jwt);
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
    
 
    @GetMapping("/client/validate")
    public ResponseEntity<Map<String, Object>> findUserByToken(@RequestParam String token) {
        try {
        	if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Long userId = tokenProvider.getUserIdFromJWT(token);

                User user = userRepository.findById(userId).orElseThrow(
                        () -> new UsernameNotFoundException("User not found with id : " + userId)
                );
                
                Map<String, Object> out = new HashMap<String, Object>() {{
                    put("username", user.getUsername());
                    put("id", user.getId());
                    put("error", 0);
                }};
                return new ResponseEntity<>(out, HttpStatus.OK);
            }
        	else {
        		Map<String, Object> out = new HashMap<String, Object>() {{
                    put("message", "token not found");
                    put("error", 1);
                }};
                return new ResponseEntity<>(out, HttpStatus.BAD_REQUEST);
        	}
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("Message", e.getMessage());
            responseMap.put("data", responseMap);
            responseMap.put("error", -1);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void logout(HttpServletRequest request) {
		String token = getJwtFromRequest(request);
		List<AccessToken> aT = accessTokenRepository.findByToken(token);
		if(aT != null) {
			accessTokenRepository.deleteAll(aT);
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
