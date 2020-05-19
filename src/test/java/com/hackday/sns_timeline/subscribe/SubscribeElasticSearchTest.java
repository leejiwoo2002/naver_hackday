package com.hackday.sns_timeline.subscribe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hackday.sns_timeline.subscribe.service.SubscribeService;

@SpringBootTest
public class SubscribeElasticSearchTest {

	@Autowired
	SubscribeService subscribeService;

	@Test
	public void addSubscribeTest() throws Exception {
		subscribeService.addSubscribe(1,2);
	}
}
