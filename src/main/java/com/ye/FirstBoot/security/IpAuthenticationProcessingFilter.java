package com.ye.FirstBoot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    //使用/ipVerify该端点进行ip认证
	public IpAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/**"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    	IpAuthenticationSuccessHandler successHandler =(IpAuthenticationSuccessHandler) this.getSuccessHandler();
    	//如果直接在登陆页面进入的，默认调到homePage 如果其他页面访问被拦截到登陆页面的，登陆过后会跳回最初的页面
    	successHandler.setDefaultTargetUrl("/login");
    	//successHandler.setAlwaysUseDefaultTargetUrl(true);
    	
        //获取host信息 
        String host = request.getRemoteHost();
        IpAuthenticationToken ipAuthenticationToken = new IpAuthenticationToken(host);
        //默认授权通过页面
        if ("/login".equals(request.getServletPath())) {
        	ipAuthenticationToken.setAuthenticated(true);
		}
        //交给内部的AuthenticationManager去认证，实现解耦
        return getAuthenticationManager().authenticate(ipAuthenticationToken);
    }
}