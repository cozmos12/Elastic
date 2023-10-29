package com.example.demo.cammunda;


import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;



import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;


import org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
@Configuration
public class CamundaSecurityFilter {

    @Bean
    public FilterRegistrationBean<Filter> processEngineAuthenticationFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setName("camunda-auth");
        registration.setFilter(getProcessEngineAuthenticationFilter());
        registration.addInitParameter("authentication-provider", HttpBasicAuthenticationProvider.class.getName());
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public Filter getProcessEngineAuthenticationFilter() {
        return (Filter) new ProcessEngineAuthenticationFilter();
    }


}

 */
