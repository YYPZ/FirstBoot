package com.ye.selfDefineBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:config/application-bean.xml"})
public class SelfDefineTest {
public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SelfDefineTest.class);
		Remote target = annotationConfigApplicationContext.getBean(Remote.class);
		target.printInfo();

	}

}