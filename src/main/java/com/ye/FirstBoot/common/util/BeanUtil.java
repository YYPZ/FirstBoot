package com.ye.FirstBoot.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class BeanUtil implements ApplicationContextAware {
	private static ApplicationContext applicatonContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicatonContext=applicationContext;
	}
	
	public static <T> T getBean(Class<T> classz) {
		return applicatonContext.getBean(classz);
		
	}

}
