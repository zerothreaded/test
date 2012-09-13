package com.dd.platform.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		System.out.println("\n\n\n setAppContext() called with [" + appContext + "]\n\n\n");
		applicationContext = appContext;
	}
	
	public static ApplicationContext getApplicationContext()  throws BeansException {
		return applicationContext;
	}

}
