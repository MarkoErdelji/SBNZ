package com.ftn.sbnz.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sbnz.exception.ApiError;
import com.ftn.sbnz.model.TokenPrincipalModel;
import com.ftn.sbnz.model.enums.UserType;
import com.ftn.sbnz.service.JwtServiceImpl;
import com.ftn.sbnz.service.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtServiceImpl jwtServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        Long userId = null;
        UserType role = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try{
                username = jwtServiceImpl.extractUsername(token);
                userId = jwtServiceImpl.extractUserId(token);
                role = jwtServiceImpl.extractRole(token);
            }catch (ExpiredJwtException e){
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(json);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
            TokenPrincipalModel tokenPrincipalModel = new TokenPrincipalModel(userId, username, role);
            if (jwtServiceImpl.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokenPrincipalModel, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }
        filterChain.doFilter(request, response);
    }

}