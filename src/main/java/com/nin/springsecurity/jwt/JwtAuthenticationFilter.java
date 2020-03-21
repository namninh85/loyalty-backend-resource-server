package com.nin.springsecurity.jwt;
/*******************************************************
 * For Vietnamese readers:
 *    Các bạn thân mến, mình rất vui nếu project này giúp 
 * ích được cho các bạn trong việc học tập và công việc. Nếu 
 * bạn sử dụng lại toàn bộ hoặc một phần source code xin để 
 * lại dường dẫn tới github hoặc tên tác giá.
 *    Xin cảm ơn!
 *******************************************************/

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nin.springsecurity.user.CustomUserDetails;
import com.nin.springsecurity.user.User;
import com.nin.springsecurity.user.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 5/1/2019
 * Github: https://github.com/loda-kun
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private JwtTokenAuthServerProvider tokenAuthServerProvider;

    @Autowired
    private UserService customUserDetailsService;
    
    @Value("${authentication_server}")
    private String authenticationServer;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
            	if (StringUtils.hasText(authenticationServer)) {
            		User userFromAuthServer = tokenAuthServerProvider.getUserFromJWT(jwt, authenticationServer);
            		
            		if(userFromAuthServer != null) {
            			customUserDetailsService.registerUserToClient(userFromAuthServer);
            			UserDetails userDetails = new CustomUserDetails(userFromAuthServer);
                        if(userDetails != null) {
                            UsernamePasswordAuthenticationToken
                                    authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                                                                             userDetails
                                                                                                     .getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
            		}
            		else {
                    	SecurityContextHolder.clearContext();
                    }

                    
            	}
            	else {
            		if( tokenProvider.validateToken(jwt)) {
                		Long userId = tokenProvider.getUserIdFromJWT(jwt);

                        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                        if(userDetails != null) {
                            UsernamePasswordAuthenticationToken
                                    authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                                                                             userDetails
                                                                                                     .getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                	}
            		else {
                    	SecurityContextHolder.clearContext();
                    }
            	}
            	
                
            }
            else {
            	SecurityContextHolder.clearContext();
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
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
