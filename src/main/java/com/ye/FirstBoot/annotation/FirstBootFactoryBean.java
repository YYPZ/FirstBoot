package com.ye.FirstBoot.annotation;

import java.lang.reflect.Field;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;

public class FirstBootFactoryBean<T> implements FactoryBean<T> {
  private String innerClassName;
  public void setInnerClassName(String innerClassName) {
      this.innerClassName = innerClassName;
  }
  public T getObject() throws Exception {
	  T tObject;
      Class innerClass = Class.forName(innerClassName);
      if (innerClass.isInterface()) {
    	 tObject=(T) InterfaceProxy.newInstance(innerClass);
          
      } else {
          Enhancer enhancer = new Enhancer();
          enhancer.setSuperclass(innerClass);
          enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
          //设置代理方法回调
          enhancer.setCallback(new MethodInterceptorImpl());
           tObject=(T) enhancer.create();
      }
      
      //通过代理的话，可以选择在此处理注解
      Field[] fields = innerClass.getDeclaredFields();
		for(Field field : fields){
			if(!field.isAccessible()){
				field.setAccessible(true);
			}
			DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
			if(defaultValue != null){
				field.set(tObject, defaultValue.value() + "_proxy");
			}
		}
      
      return tObject;
      
      
  }
  public Class<?> getObjectType() {
      try {
            return Class.forName(innerClassName);
      } catch (ClassNotFoundException e) {
            e.printStackTrace();
      }
      return null;
  }
  public boolean isSingleton() {
      return true;
  }
  public void afterPropertiesSet() throws Exception {
  }
}
