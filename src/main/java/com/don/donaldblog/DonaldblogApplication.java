package com.don.donaldblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.don.donaldblog.mapper")
public class DonaldblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonaldblogApplication.class, args);
	}

}
