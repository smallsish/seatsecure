package com.seatsecure.backend.filter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/api/v1/users/*")
public class CookieLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException, java.io.IOException {
        // Cast the request to HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Retrieve and log the cookies
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println();
                System.out.println("Cookie name: " + cookie.getName());
                System.out.println("Cookie value: " + cookie.getValue());
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("No Cookies found in Request");
            System.out.println();
        }
        if (httpRequest.getHeader("cookieTest") != null) {
            System.out.println(httpRequest.getHeader("cookieTest"));
        }
        // Continue processing the request
        chain.doFilter(request, response);
    }

    // Implement the other methods from the Filter interface
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
