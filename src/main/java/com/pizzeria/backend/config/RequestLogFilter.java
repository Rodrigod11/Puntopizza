package com.pizzeria.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@Order(100) // se ejecuta después del JwtAuthenticationFilter
public class RequestLogFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String user = (auth != null) ? auth.getName() : "ANON";
    String roles = (auth != null && auth.getAuthorities() != null)
        ? auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","))
        : "(sin autoridades)";

    log.info("[SEC] {} {} -> user={}, authorities={}", request.getMethod(), request.getRequestURI(), user, roles);

    filterChain.doFilter(request, response);
  }
}
