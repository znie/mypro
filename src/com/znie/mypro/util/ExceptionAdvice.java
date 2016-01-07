package com.znie.mypro.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionAdvice {
	
	/**
	 * “Ï≥£¥¶¿Ì
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView exceptionHandler(Exception ex) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		System.out.println("in ExceptionAdvice");
		return mv;
	}
}
