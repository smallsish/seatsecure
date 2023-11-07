package com.seatsecure.backend.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.seatsecure.backend.filter.CookieLoggingFilter;

@Configuration
public class FilterConfig {
    @Bean
    public CookieLoggingFilter cookieLoggingFilter() {
        return new CookieLoggingFilter();
    }

    @Bean
    public FilterRegistrationBean<CookieLoggingFilter> loggingFilter() {
        FilterRegistrationBean<CookieLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(cookieLoggingFilter());
        // registrationBean.addUrlPatterns("/api/v1/users/*"); // Specify the URL pattern you want to filter
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
