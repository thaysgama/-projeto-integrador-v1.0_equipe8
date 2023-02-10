package com.dh.digitalbooking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    public static final String ATTRIBUTE_PREFIX = "Bearer ";
    private final Logger log = Logger.getLogger(JWTValidationFilter.class);

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        if(request.getServletPath().equals("/api/v1/login") || request.getServletPath().equals("/api/v1/user/refresh/token")){
            chain.doFilter(request,response);
        } else {
            String attribute = request.getHeader(AUTHORIZATION);
            if(attribute==null || !attribute.startsWith(ATTRIBUTE_PREFIX)){
                chain.doFilter(request,response);
                return;
            }

            try {
                String token = attribute.replace(ATTRIBUTE_PREFIX, "");

                UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                chain.doFilter(request,response);

            }catch (Exception exception){
                log.error("Error loggin in: {}"+ exception.getMessage());
                response.setHeader("error_message", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
        DecodedJWT decoderJWT = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_PASSWORD))
                                .build()
                                .verify(token);
        String user = decoderJWT.getSubject();
        String [] roles = decoderJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role-> authorities.add(new SimpleGrantedAuthority(role)));

        if(user == null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(user, null,authorities);
    }










}
