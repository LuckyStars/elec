package com.nbcedu.function.elec.devcore.util;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * <p>获得Spring上下文中的Bean的工具类</p>
 * 
 * @author Administrator
 * @since 2012-11-21
 */
public class SpringBeanService implements Serializable {
	
	private static final long serialVersionUID = -3563623148543126234L;
	
	/** 
	 * 获得Spring上下文中的Bean
	 * @param <T> 获得的Bean的类型
	 * @param beanName 要获得的Bean的名称
	 */ 
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz, String beanName) {  
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();  
        return (T) context.getBean(beanName);  
    }
	
	public Object getBeanByName(String beanName) {  
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();  
        return context.getBean(beanName);  
    }
	
}
