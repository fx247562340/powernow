package com.powernow.cdt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.powernow.cdt.dao")
public class PowernowApplication {

	public static void main(String[] args) {
		SpringApplication.run(PowernowApplication.class, args);
	}

}
