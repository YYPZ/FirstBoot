package com.ye.selfDefineBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SelfDefineTest {
public static void main(String[] args) {
	 ApplicationContext context=new ClassPathXmlApplicationContext("classpath:config/application-bean.xml");
	 Remote target = (Remote) context.getBean(Remote.class);
		target.printInfo();

	}

}