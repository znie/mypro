package com.znie.mypro.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class HelloClient {
	public static void main(String[] args) {
		try {
			Client client = ClientBuilder.newClient();

			WebTarget target = client
					.target("http://localhost:8081/mypro/service/hello")
					.path("/post").queryParam("test", "111");

			Response res = target.request().accept("application/json").get();

			if (res.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ res.getStatus());
			}
			String output = res.readEntity(String.class);

			System.out.println(res.getStatus() + "\n¡­¡­Output from Server¡­¡­");
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
