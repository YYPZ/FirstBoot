package com.ye.FirstBoot.annotation;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public final class Scanner extends ClassPathBeanDefinitionScanner {
	
  public Scanner(BeanDefinitionRegistry registry) {
      super(registry);
  }
  
  public void registerDefaultFilters() {
	  //默认扫描哪个注解注释的类
      this.addIncludeFilter(new AnnotationTypeFilter(FirstBootService.class));
  }
  
  public Set<BeanDefinitionHolder> doScan(String... basePackages) {
      Set<BeanDefinitionHolder> beanDefinitions =   super.doScan(basePackages);
      for (BeanDefinitionHolder holder : beanDefinitions) {
          GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
          /* 这两行代码顺序不能改变
           * 设置FirstBootFactoryBean 的 innerClassName的值，然后指定definition定义bean的的实例工厂
           * ApplicationContext.getBean(Target.class)都会由指定 实例工厂产生实例
           */
          definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
          definition.setBeanClass(FirstBootFactoryBean.class);
      }
      return beanDefinitions;
  }
  
  public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
     return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata()
				.hasAnnotation(FirstBootService.class.getName());
  }
}