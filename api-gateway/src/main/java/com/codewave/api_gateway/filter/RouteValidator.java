//package com.codewave.api_gateway.filter;
//
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//@Component
//public class RouteValidator {
//
//    //These are routes that don’t need JWT authentication.
//    public static final List<String> openApiEndpoints = List.of(
//            "/api/auth/register",
//            "/api/auth/login",
//            "/eureka",
//            "/swagger-ui"
//    );
//
//    public Predicate<ServerHttpRequest> isSecured =
//            request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
//}
