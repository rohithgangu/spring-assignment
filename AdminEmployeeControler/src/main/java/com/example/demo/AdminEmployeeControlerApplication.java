package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;




@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class AdminEmployeeControlerApplication {

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter());
		template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return template;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AdminEmployeeControlerApplication.class, args);
	}
	
	

	
}
