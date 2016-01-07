package com.znie.mypro.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoaderListener;



public class LoaderListener extends ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		
		ServletContext sc = servletContextEvent.getServletContext();
		sc.setAttribute("path", sc.getContextPath());
		ServletContentCache.getInstance().setSc(sc);
		
	}
	
	
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
		ServletContext sc = servletContextEvent.getServletContext();	
		sc.removeAttribute("path");
		ServletContentCache.getInstance().setSc(null);
		closeWebApplicationContext(sc);
		
		
	}
	
}
