package com.znie.mypro.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSON;

@Path("/hello")
public class HelloService {
	
	@GET
	@Path("/index")
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		Map m = new HashMap();
		m.put("hello","world");
		m.put("hello2","world");
		return JSON.toJSONString(m);
	}
	
	@GET
	@Path("/post")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String post(@QueryParam("test")String test) {
		Map<String,String> m = new HashMap<String,String>();
		m.put("test",test);
		m.put("hello2","world");
		return JSON.toJSONString(m);
	}
}
