package com.huigege.pig.geteway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GetewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetewayApplication.class, args);
	}
}
