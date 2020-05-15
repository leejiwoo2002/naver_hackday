package com.hackday.sns_timeline.profile;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.profile.service.ProfileService;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.service.SignService;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileTest {

	@Autowired
	ProfileService profileService;

	@Autowired
	SignService signService;

	@Autowired
	SubscribeRepository subscribeRepository;


	@Test
	@Transactional
	public void getSubscribeList() throws Exception {

		String name = "newTest";
		int count = 10;

		Member member = signService.signUp(MemberDto.builder()
			.email(name + "@test")
			.name(name)
			.password("test")
			.build());

		List<Member> testMemberList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			testMemberList.add(signService.signUp(MemberDto.builder()
				.email(name+i + "@test")
				.name(name+i)
				.password("test")
				.build()));
		}

		for (Member member1 : testMemberList) {
			subscribeRepository.save(Subscribe.builder().member(member).subscribeMember(member1).build());
		}

		List<MemberDto> subscribeMember = profileService.getSubscribeMember(member);

		assertThat(subscribeMember.size()).isEqualTo(11);
	}
}
