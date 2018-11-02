package com.ye.FirstBoot.common.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //允许 监控actuator 通过安全校验
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/monitor/*").permitAll().anyRequest().authenticated();
    	
      /*  http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
            .anyRequest().permitAll();*/
    }
    

}