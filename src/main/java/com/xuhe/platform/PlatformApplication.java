package com.xuhe.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.xuhe.platform.dal.mapper"})
@SpringBootApplication
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

}
