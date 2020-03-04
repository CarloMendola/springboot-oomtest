package oomtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OomtestApplication {
	static Logger log = LogManager.getLogger(OomtestApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(OomtestApplication.class, args);			
	}

}
