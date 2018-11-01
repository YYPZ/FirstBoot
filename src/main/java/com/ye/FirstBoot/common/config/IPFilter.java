package com.ye.FirstBoot.common.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPFilter implements Filter {
		Logger logger = LoggerFactory.getLogger(getClass());
		String allowIP;
		String filterName;
		@Override
		public void destroy() {
			logger.info("Filter destroy");
		}

		@Override
		public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
				throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) srequest;
			//WebConfiguration的配置
			logger.info(filterName+": access URL "+request.getRequestURI());
			logger.info(filterName+": allowIP "+allowIP +" clientIP "+request.getRemoteAddr());
			
			
			filterChain.doFilter(srequest, sresponse);
		}

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			allowIP=filterConfig.getInitParameter("allowAccessIP");
			filterName=filterConfig.getFilterName();
			logger.info("Filter init");
		}
    }