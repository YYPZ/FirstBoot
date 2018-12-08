package com.ye.FirstBoot.annotation;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware,BeanPostProcessor,InitializingBean {
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Scanner scanner = new Scanner((BeanDefinitionRegistry) beanFactory);
		scanner.setResourceLoader(this.applicationContext);
		//配置扫描哪个包
		scanner.scan("com.ye.FirstBoot.annotation");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		/*
		 * 如果没有使用自定义代理（ definition.setBeanClass(FirstBootFactoryBean.class);）
		 * 生产bean实例，默认在此可扫描DefaultValue注解注释的属性，赋予默认值
		 */
		Field[] fields = bean.getClass().getDeclaredFields();
		for(Field field : fields){
			if(!field.isAccessible()){
				field.setAccessible(true);
			}
			DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
			if(defaultValue != null){
				try {
					field.set(bean, defaultValue.value());
				} catch (Exception e) {
					throw new BeanInitializationException(e.getMessage(),e);
				} 
			}
		}

		return bean;
	}
}