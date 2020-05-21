package com.hackday.sns_timeline.profile;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.profile.controller.ProfileController;
import com.hackday.sns_timeline.profile.service.ProfileService;
import com.hackday.sns_timeline.sign.domain.Role;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.sign.service.SignService;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileController.class)
public class ProfileTest {
	@MockBean
	SignService signService;

	@MockBean
	SubscribeRepository subscribeRepository;

	@MockBean
	MemberRepository memberRepository;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProfileService profileService;

	@BeforeEach
	public void before(){
	}

	@Test
	@WithMockUser(username = "test@test", password = "test", roles = "MEMBER")
	public void getProfileTest() throws Exception {
		mockMvc.perform(get("/profile").accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk());
	}

	@AfterEach
	public void after(){

	}

	// @Test
	// @Transactional
	// public void getSubscribeList() {
	//
	// 	String name = "newTest";
	// 	int count = 10;
	//
	// 	Member member = signService.signUp(MemberDto.builder()
	// 		.email(name + "@test")
	// 		.name(name)
	// 		.password("test")
	// 		.build());
	//
	// 	List<Member> testMemberList = new ArrayList<>();
	// 	for (int i = 0; i < count; i++) {
	// 		testMemberList.add(signService.signUp(MemberDto.builder()
	// 			.email(name+i + "@test")
	// 			.name(name+i)
	// 			.password("test")
	// 			.build()));
	// 	}
	//
	// 	for (Member member1 : testMemberList) {
	// 		subscribeRepository.save(Subscribe.builder().memberId(member.getId())
	// 			.subscribeMemberId(member1.getId()).build());
	// 	}
	//
	// 	List<MemberDto> subscribeMember = profileService.getSubscribeMember(member);
	//
	// 	assertThat(subscribeMember.size()).isEqualTo(11);
	// }
}
