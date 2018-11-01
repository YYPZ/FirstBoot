package com.ye.FirstBoot.common.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
	
	/*
	 * 当客户端通过HTTP代理或者负载均衡访问应用服务器时，对于服务器来说，请求直接源自前置的代理服务器，那么此时获取到的远程IP实际为代理服务器的IP地址。
	 * HTTP协议通过“X-Forwarded-For“头信息记录了客户端到应用服务器前置代理的IP路径，因此可以通过该属性获知客户端的IP地址。
	 * Tomcat通过org.apache.catalina.filtersRemoteIpFilter实现了该功能。解析了参数，替换为真实请求的IP，
	 * 通过ServletRequest.getRemoteAddr和ServletRequest.getRemoteHost获取相关信息。

         X-Forwarded-For的格式一般如下：X-Forwarded-For:client,proxy1,proxy2

	 RemoteIpFilter支持以下初始化参数
         remoteIpHeader:HTTP请求头名称，过滤器将从该请求头读取IP地址完成转换，默认为X-Forwarded-For

         internalProxies:IP地址的正则表达式，代理服务器IP地址匹配该表达式时将视为内部代理。

         proxiesHeader:HTTP请求头名称，用于保存当前过滤器处理的remoteIpHeader中记录的代理服务器列表。默认为X-Forwarded-By

         requestAttributesEnabled:如果为true,过滤器将使用转换后的值覆盖AccessLog中使用的相关请求属性（IP地址，主机，端口号，协议）。默认为true.

         trustedProxies:正则表达式，如果代理服务器的IP地址匹配该表达式，那么将被视为可信任代理。remoteIpHeader中记录的可信任代理将会被包含到proxiesHeader中，如果不指定所有代理均不被信任。

         protocolHeader:HTTP请求头名称，用于记录客户端链接代理所使用的协议。默认为空

         portHeader:HTTP请求头名称，用于记录客户端链接代理所使用的端口。默认为空。

         protocolHeaderHttpsValue:当protocolHeader对应请求头的取值等于该属性值时，tomcat将请求类型视为HTTPS。默认为https

         httpServerPort:当protocolHeader对应请求头表明是HTTPS请求，但是portHeader对应请求头没有设置时，ServletRequest.getServerPort的返回值。默认为443

         changeLocalPort:如果为true，portHeader对应请求头保存的端口号将会覆盖Servlet-Request.getLocalPort()的返回值。默认为false
	 *
	 */
    @Bean
    public RemoteIpFilter remoteIpFilter() {
    	RemoteIpFilter remoteIpFilter=  new RemoteIpFilter();
        return remoteIpFilter;
    }
    
    @Bean
    public FilterRegistrationBean addFilterRegistration() {
    	//与IPFilter结合使用
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new IPFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("allowAccessIP", "172.16.4.177");
        registration.setName("IPFilter");
        registration.setOrder(1);
        return registration;
    }
 
}



