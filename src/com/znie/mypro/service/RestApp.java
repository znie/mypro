package com.znie.mypro.service;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/")
public class RestApp  extends ResourceConfig{
	
	public RestApp(){
		// ���������ڵİ�·��
		packages("com.znie.mypro.service");
	}

}
