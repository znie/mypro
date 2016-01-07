package com.znie.mypro.util;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MyBeanFactroy {
	static WebApplicationContext wct;
	static {
		wct = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletContentCache
						.getInstance().getSc());
	}

	public static Object getBean(String name) {
		return wct.getBean(name);
	}
}
