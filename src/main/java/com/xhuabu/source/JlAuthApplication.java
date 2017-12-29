package com.xhuabu.source;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@MapperScan(value = {"com.xhuabu.source.dao"})
@EnableConfigurationProperties
@SpringBootApplication
public class JlAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(JlAuthApplication.class, args);
	}
}
