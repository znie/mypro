package com.znie.mypro.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/")
public class RestApp  extends ResourceConfig{
	
	public RestApp(){
		// 服务类所在的包路径
		packages("com.znie.mypro.service");
	}

}
