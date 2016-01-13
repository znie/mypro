package com.znie.mypro.mongodb;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mongo")
public class MongoController {

	@Resource(name = "mongoUtil")
	MongoUtil mongoUtil;

	@RequestMapping("/index")
	public void helloMongo(HttpServletRequest request,
			HttpServletResponse response) {
		List<Document> list = mongoUtil.findList("tf_uc_domain",
				new Document(), null, null, null, null);
		System.out.println(list);
	}
}
