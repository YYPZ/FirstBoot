package com.ye.FirstBoot.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ye.FirstBoot.annotation")
public class CustomizeScanAnnotationTest {
public static void main(String[] args) {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(CustomizeScanAnnotationTest.class);                
    Target target = annotationConfigApplicationContext.getBean(Target.class);
    target.printInfo();
   	
}
 

 }