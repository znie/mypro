package com.znie.mypro.test;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.znie.mypro.util.MyBeanFactroy;

@Controller
@RequestMapping("/test/hello")
public class HelloController {

	/**
	 * shijian
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}


	@RequestMapping("/date")
	public String date(Date date, HttpServletRequest request,
			HttpServletResponse response) {
//		int i= 5/0;
		System.out.println(date);
		return "test/hello/index";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		HelloDao helloDao = (HelloDao)MyBeanFactroy.getBean("helloDao");
		helloDao.getHello();
		return "test/hello/index";
	}

}
