package com.codewave.auth_service.service.jwt;

import com.codewave.auth_service.service.impl.UserCredentialsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserCredentialsService userCredentialsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get the Authorization header that usually contains the JWT token in the format "Bearer <token>"
        final String authHeader = request.getHeader("Authorization");

        //initialize username and jwtToken variables for later use
        String username = null;
        String jwtToken = null;

        // If Authorization header starts with "Bearer ", extract the JWT part (after the 7th character)
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
            username = jwtService.extractUsername(jwtToken);
        }

        //check if the user is already authenticated or not means if the user is already authenticated
        //the security context has user authenticated object if not it has null then we are good to go with
        //authenticate the user otherwise no need to authenticate as the user already authenticated
        //so that it prevents re-authenticating users who are already verified in this request.
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //Get the user details from database which will have username, password and authorities(Roles and Privileges)
            UserDetails userDetails = this.userCredentialsService.loadUserByUsername(username);
            /*
            1. Validate username extracted from token against the username from database
            2. Validate if the token is expired or not
            3. Validate token signature
            * */
            if(jwtService.validateToken(userDetails, jwtToken)){

                // Create an Authentication(UsernamePasswordAuthenticationToken) object representing the verified user
                // and store it in the SecurityContext, marking the user as authenticated.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // Store the authenticated user details in the SecurityContext so that downstream filters and controllers can access it.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue with the next filter in the chain (e.g., reaching controller endpoints if authentication passed)
        filterChain.doFilter(request, response);
    }
}
