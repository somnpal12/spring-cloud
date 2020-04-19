package com.sample.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sample.constant.SecurityConstants;
import com.sample.service.UserAuthenticationService;
import com.sample.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import static com.sample.constant.SecurityConstants.*;
import static com.sample.util.JWTUtil.extractUserName;

public class AuthFilter extends BasicAuthenticationFilter {


    UserAuthenticationService userAuthenticationService;


    public AuthFilter(AuthenticationManager authenticationManager, UserAuthenticationService userAuthenticationService) {
        super(authenticationManager);
        this.userAuthenticationService = userAuthenticationService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

       String authorizationHeader = request.getHeader(HEADER_STRING);
        String[] credentials = null;
        String encodedUser = null;
        if (StringUtils.contains(authorizationHeader, TOKEN_PREFIX_BASIC)) {
            encodedUser = new String(Base64.getDecoder().decode(StringUtils.substringAfter(authorizationHeader, TOKEN_PREFIX_BASIC)));
            credentials = StringUtils.split(encodedUser, ":");

            Authentication authentication = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]));
            onSuccessfulAuthentication(request,response,authentication);

        }else if(StringUtils.contains(authorizationHeader, TOKEN_PREFIX)) {
            String token = StringUtils.substringAfter(authorizationHeader, TOKEN_PREFIX);
            String username = extractUserName(token);
            if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userAuthenticationService.loadUserByUsername(username);

                if (JWTUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        chain.doFilter(request, response);
    }


    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = JWT.create()
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}
