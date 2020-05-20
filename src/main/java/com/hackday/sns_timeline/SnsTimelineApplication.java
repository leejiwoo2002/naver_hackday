package com.hackday.sns_timeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SnsTimelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsTimelineApplication.class, args);
	}

}
