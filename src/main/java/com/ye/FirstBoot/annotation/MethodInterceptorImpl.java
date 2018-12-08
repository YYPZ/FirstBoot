package com.ye.FirstBoot.annotation;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class MethodInterceptorImpl implements MethodInterceptor {
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("MethodInterceptorImpl before call:" + method.getName());
		Object object=methodProxy.invokeSuper(o, objects);
		System.out.println("MethodInterceptorImpl after call:" + method.getName());
		return object;
	}
}