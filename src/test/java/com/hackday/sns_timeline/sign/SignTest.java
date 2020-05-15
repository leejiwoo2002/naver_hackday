package com.hackday.sns_timeline.sign;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.sign.service.SignService;

@SpringBootTest
public class SignTest {

	@Autowired
	SignService signService;

	@Autowired
	MemberRepository memberRepository;

	@Test
	@Transactional
	public void signUpTest() throws Exception{

		Long maxId = memberRepository.findMaxId();

		Member newMember = signService.signUp(
			MemberDto.builder().email("test" + maxId + 1 + "@test").name("test").password("test").build());

		assertThat(memberRepository.findByEmail(newMember.getEmail()).isPresent()).isTrue();

	}
}
