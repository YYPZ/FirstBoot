package com.ye.FirstBoot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*@Autowired
	MyAuthenticationSuccessHandler  successHandler;
	*/
	//ip认证者配置
    @Bean
    IpAuthenticationProvider ipAuthenticationProvider() {
        return new IpAuthenticationProvider();
    }
    
    //配置登录端点
    @Bean
    LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint
                ("/login");
        return loginUrlAuthenticationEntryPoint;
    }

  /*  //配置登录端点
    @Bean
    LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        LoginUrlAuthenticationEntryPoint entryPoint = new MyLoginUrlAuthenticationEntryPoint ("/homePage");
        return entryPoint;
    }*/
    
  /*  //配置登录端点
    @Bean
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler(){
    	MyAuthenticationSuccessHandler successHandler = new MyAuthenticationSuccessHandler();
        return successHandler;
    }
*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/monitor/*").permitAll()
                .antMatchers("/","/login","/denyAll").permitAll()
                .anyRequest().authenticated()
                .and()
          /*  .formLogin()
            	.successHandler(successHandler)
                .and()*/
            .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/denyAll")
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
       
        ;

        //注册IpAuthenticationProcessingFilter  注意放置的顺序 这很关键 放到密码认证之前
        http.addFilterBefore(ipAuthenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
        .csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ipAuthenticationProvider());
    }
    

    //配置封装ipAuthenticationToken的过滤器
    IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter = new IpAuthenticationProcessingFilter();
        //为过滤器添加认证器
        ipAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        //重写认证失败时的跳转页面
        ipAuthenticationProcessingFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error=loging error"));
        return ipAuthenticationProcessingFilter;
    }


}