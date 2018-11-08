package com.ye.FirstBoot.security;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;  
  
public class MyAuthenticationSuccessHandler implements  
        AuthenticationSuccessHandler {
	@Value("${defaultForward}")
	String defaultForward;
  
    @Override  
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {        
	    request.getSession().setAttribute("isLogin", "true");
        if ("/login".equals(request.getServletPath())) {
        	request.getRequestDispatcher(defaultForward).forward(request, response);
		}
    
    }  
  
} 