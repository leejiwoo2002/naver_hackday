package com.hackday.sns_timeline;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hackday.sns_timeline.domain.entity.SubscribePK;
import com.hackday.sns_timeline.repository.MemberRepository;
import com.hackday.sns_timeline.repository.SubscribeRepository;
import com.hackday.sns_timeline.service.SubscribeService;

@SpringBootTest
public class SubscribeTest {

	@Autowired
	SubscribeService subscribeService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	SubscribeRepository subscribeRepository;

	@Test
	public void addSubscribe() throws Exception{
		subscribeService.addSubscribe(7,8);

		assertThat(subscribeRepository.findById(SubscribePK.builder().subscriberId((long)1).subscribedMemberId((long)2).build()).isPresent())
			.isTrue();
	}
}
