package com.hackday.sns_timeline.subscribe;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hackday.sns_timeline.subscribe.domain.entity.SubscribePK;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;

@SpringBootTest
public class SubscribeTest {

	@Autowired
	SubscribeService subscribeService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	SubscribeRepository subscribeRepository;

	@Test
	public void addSubscribe() throws Exception {
		subscribeService.addSubscribe(7, 8);

		assertThat(
			subscribeRepository.findById(SubscribePK.builder().userId((long)1).subscribeTargetId((long)2).build())
				.isPresent())
			.isTrue();
	}
}
