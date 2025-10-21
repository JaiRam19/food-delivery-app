//package com.codewave.productservice.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.security.Key;
//import java.util.*;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Value("${secret.key}")
//    private String SECRET_KEY;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        //Get the Authorization header that usually contains the JWT token in the format "Bearer <token>"
//        final String authHeader = request.getHeader("Authorization");
//
//        // If Authorization header starts with "Bearer ", extract the JWT part (after the 7th character)
//        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //extract only jwt token
//        String jwtToken = authHeader.substring(7);
//
//        //extract all claims from the token
//        Claims claims = extractAllClaims(jwtToken);
//
//        //check expiration of the token
//        if(claims.getExpiration().before(new Date(System.currentTimeMillis()))){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //get username from the claims
//        String username = claims.getSubject();
//
//        //Get roles and privileges and other authorities
//       List<String> permissions = claims.get("authorities", List.class);
//        System.out.println(username+" has permission "+permissions);
//       //Map to authorities
//       Collection<GrantedAuthority> authorities = new ArrayList<>();
//       permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
//
//        // create a principal with custom attributes if you want
//        Map<String, Object> principalDetails = Map.of(
//                "username", username);
//
//        if(SecurityContextHolder.getContext().getAuthentication() == null){
//
//                // Create an Authentication(UsernamePasswordAuthenticationToken) object representing the verified user
//                // and store it in the SecurityContext, marking the user as authenticated.
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        principalDetails, null, authorities
//                );
//
//                // Store the authenticated user details in the SecurityContext so that downstream filters and controllers can access it.
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//
//        // Continue with the next filter in the chain (e.g., reaching controller endpoints if authentication passed)
//        filterChain.doFilter(request, response);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSignKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }
//
//}
