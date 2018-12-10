package com.ye.selfDefineBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class NamespaceHandler extends NamespaceHandlerSupport {
	
	private static Log logger = LogFactory.getLog(NamespaceHandler.class);

	public void init() {
		logger.info("开始初始化自定义Remote标签...");
		registerBeanDefinitionParser("remote", new FirstBootSelfDefineBeanDefinitionParser(Remote.class, true));
		logger.info("初始化自定义Remote标签结束...");
	}

}