package com.example.demo.config.interceptor;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
@Order(1)
public class PostFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var wrappedRequest = new WrappedRequest(request);
        System.out.println(printRequest(wrappedRequest));
        filterChain.doFilter(wrappedRequest, response);
    }

    private String printRequest(WrappedRequest wrappedRequest) throws IOException {
        final var sb = new StringBuilder("\n");
        sb.append(wrappedRequest.getMethod()).append(" ").append(wrappedRequest.getRequestURI()).append(" ").append(wrappedRequest.getProtocol()).append("\n");
        Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            sb.append(headerName).append(" : ").append(wrappedRequest.getHeader(headerName)).append("\n");
        }
        sb.append("\n");
        sb.append(wrappedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        return sb.toString();
    }

}
