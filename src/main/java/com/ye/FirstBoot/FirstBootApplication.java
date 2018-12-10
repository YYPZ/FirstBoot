package com.ye.FirstBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("com.ye.FirstBoot.dataAccess.mybatis.dao")
@PropertySource("classpath:dataSource.properties")
@PropertySource("classpath:redis.properties")
@ImportResource("classpath:config/application-bean.xml")
public class FirstBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstBootApplication.class, args);
	}
}
