//package com.codewave.api_gateway.filter;
//
//import org.apache.http.HttpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.server.HttpServerState;
//
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    private final RouteValidator routeValidator;
//    /*@Autowired
//    private RestTemplate template;*/
//    private final WebClient.Builder webClientBuilder;
//
//    public AuthenticationFilter(RouteValidator routeValidator, WebClient.Builder webClientBuilder) {
//        super(Config.class);
//        this.routeValidator = routeValidator;
//        this.webClientBuilder = webClientBuilder;
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (((exchange, chain) -> {
//            System.out.println("AuthenticationFilter::apply method execution started ");
//            if (!routeValidator.isSecured.test(exchange.getRequest())) {
//                return chain.filter(exchange);
//            }
//
//            //1. Check header presence correctly
//            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                return this.onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
//            }
//
//            System.out.println("Header passed");
//
//            //2. Extract and validate Bearer token
//            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
//
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return this.onError(exchange, "Unauthorized to access the application", HttpStatus.UNAUTHORIZED);
//            }
//
//            System.out.println("Bearer passed");
//            authHeader = authHeader.substring(7);000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
//
//
//            //3. Validate token with Auth Service
//            //String result = template.getForObject("http://localhost:8086/validate?token="+authHeader, String.class);
//            /*return webClientBuilder.build()
//                    .get()
//                    .uri("http://AUTH-SERVICE/validate?token=" + authHeader)
//                    //.uri("http://localhost:8086/api/auth/validate?token="+authHeader)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .flatMap(response -> {
//                        if (response != null && response.equalsIgnoreCase("Token is Valid")) {
//                            System.out.println("Token validated successfully");
//                            return chain.filter(exchange);
//                        } else {
//                            System.err.println("Token validation failed: " + response);
//                            return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
//                        }
//                    })
//                    .onErrorResume(ex -> {
//                        System.err.println("Token validation error: " + ex.getMessage());
//                        return this.onError(exchange, ex.getMessage(), HttpStatus.UNAUTHORIZED);
//                    });*/
//
//            return webClientBuilder.build()
//                    .get()
//                    // Using a more likely full path, adjust if your Auth Service is different
//                    .uri("http://AUTH-SERVICE/api/auth/validate?token=" + authHeader)
//                    .retrieve()
//
//                    // Crucial: Handle 4xx responses (like 401) from the Auth Service
//                    // If Auth Service returns 4xx, it means token validation failed.
//                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
//                        System.err.println("Token validation failed in Auth Service (Status: " + clientResponse.statusCode() + ")");
//                        // Propagate an UNAUTHORIZED status back to the client
//                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token reported by Auth Service"));
//                    })
//
//                    // Discard the body, we only care about the 2xx status for success
//                    .bodyToMono(Void.class)
//
//                    // If validation succeeds (2xx status), proceed with the filter chain
//                    .then(Mono.defer(() -> {
//                        System.out.println("Token validated successfully (Auth Service returned 2xx)");
//                        // You can optionally add user info to the request headers here before chaining
//                        return chain.filter(exchange);
//                    }))
//
//                    // Catch network/timeout errors or the ResponseStatusException thrown in onStatus
//                    .onErrorResume(ex -> {
//                        String errorMessage = "Authentication service communication error.";
//                        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Use 500 for network issues
//
//                        if (ex instanceof ResponseStatusException) {
//                            // This handles the UNAUTHORIZED error thrown by onStatus
//                            errorMessage = ((ResponseStatusException) ex).getReason();
//                            status = (HttpStatus) ((ResponseStatusException) ex).getStatusCode();
//                        } else {
//                            System.err.println("WebClient Network Error: " + ex.getMessage());
//                        }
//
//                        return this.onError(exchange, errorMessage, status);
//                    });
//
//
//        }));
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
//        exchange.getResponse().setStatusCode(status);
//        return exchange.getResponse().setComplete();
//    }
//
//    public static class Config {
//    }
//}
