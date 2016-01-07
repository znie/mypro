package com.znie.mypro.test;

import org.springframework.stereotype.Component;

@Component
public class HelloDao {

	public void getHello(){
		System.out.println("##########DAO SAY Hello##########");
	}
}
