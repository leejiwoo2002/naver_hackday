package com.hackday.sns_timeline.subscribe;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.service.SignService;
import com.hackday.sns_timeline.subscribe.controller.SubscribeController;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.domain.entity.SubscribePK;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;

@SpringBootTest
@AutoConfigureMockMvc
public class SubscribeTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	SubscribeService subscribeService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	SubscribeRepository subscribeRepository;

	@Autowired
	SignService signService;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(SubscribeController.class)
			.alwaysExpect(MockMvcResultMatchers.status().isOk())
			.build();
	}

	@Test
	@Transactional
	public void addSubscribe() throws Exception {

		Long maxId = memberRepository.findMaxId();

		Member member = memberRepository.findById(maxId).get();

		Member newMember = signService.signUp(
			MemberDto.builder().email("test" + maxId + 1 + "@test").name("test").password("test").build());

		subscribeService.addSubscribe(member.getId(), newMember.getId());

		assertThat(
			subscribeRepository.findById(SubscribePK.builder().userId(member.getId()).subscribeTargetId(newMember.getId()).build())
				.isPresent())
			.isTrue();
	}

	@Test
	@Transactional
	public void deleteSubscribe() throws Exception {
		List<Subscribe> all = subscribeRepository.findAll();
		Subscribe subscribe = all.get(0);

		subscribeService.deleteSubscribe(subscribe.getSubscribePK().getUserId()
			, subscribe.getSubscribePK().getSubscribeTargetId());

		assertThat(
			subscribeRepository.findById(subscribe.getSubscribePK())
				.isPresent())
			.isFalse();
	}
}
