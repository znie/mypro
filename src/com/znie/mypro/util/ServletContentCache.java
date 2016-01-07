package com.znie.mypro.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class ServletContentCache {
	
	

	private ServletContentCache() {

	}

	static class SingleTon {
		static final ServletContentCache instance = new ServletContentCache();
	}

	public static ServletContentCache getInstance() {
		return SingleTon.instance;
	}

	private ServletContext sc;

	public ServletContext getSc() {
		return sc;
	}

	public void setSc(ServletContext sc) {
		this.sc = sc;
	}
	
	
	
}
