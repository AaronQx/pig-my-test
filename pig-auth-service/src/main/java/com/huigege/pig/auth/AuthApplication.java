package com.huigege.pig.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * @author lengleng
 *         获取用户信息也是通过这个应用实现
 *         这里既是认证服务器，也是资源服务器
 *         EnableResourceServer
 */
@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableFeignClients
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
