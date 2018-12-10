package com.ye.selfDefineBean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class FirstBootSelfDefineBeanDefinitionParser extends AbstractBeanDefinitionParser {
	private Class<?> beanClass;
	private boolean required;

	public FirstBootSelfDefineBeanDefinitionParser(Class<?> clazz, boolean required) {
		this.beanClass = clazz;
		this.required = required;
	}

	
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);
		String ip=element.getAttribute("ip");  
        String port=element.getAttribute("port");  
        beanDefinition.getPropertyValues().add("ip", ip);
        beanDefinition.getPropertyValues().add("port", port);

		return beanDefinition;
	}

}
