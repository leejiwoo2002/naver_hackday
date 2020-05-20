package com.hackday.sns_timeline.subscribe;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberEsRepository;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.sign.service.SignService;
import com.hackday.sns_timeline.subscribe.domain.document.SubscribeDoc;
import com.hackday.sns_timeline.subscribe.repository.SubscribeEsRepository;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;

@SpringBootTest
public class SubscribeElasticSearchTest {

	@Autowired
	SubscribeService subscribeService;

	@Autowired
	SubscribeEsRepository subscribeEsRepository;

	@Autowired
	SignService signService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	SearchMemberEsRepository searchMemberEsRepository;


	@BeforeEach
	public void before() throws Exception {
		signService.signUp(MemberDto.builder().email("new@new").name("new").password("test").build());
		signService.signUp(MemberDto.builder().email("new2@new").name("new2").password("test").build());
	}

	@Test
	@Transactional
	public void addSubscribeTest() throws Exception {
		Member test1 = memberRepository.findByEmail("new@new").get();
		Member test2 = memberRepository.findByEmail("new2@new").get();

		subscribeService.addSubscribe(test1.getId(), test2.getId());

		SubscribeDoc subscribeDoc = subscribeEsRepository.findByMemberIdAndSubscribeMemberId(
			test1.getId(), test2.getId()).get();

		List<SubscribeDoc> byMemberId = subscribeEsRepository.findByMemberId(test1.getId());

		assertThat(subscribeDoc.getMemberId()).isEqualTo(test1.getId());
		assertThat(subscribeDoc.getSubscribeMemberId()).isEqualTo(test2.getId());
		assertThat(byMemberId.size()).isEqualTo(2);
	}

	@AfterEach
	public void after(){
		List<SearchMemberDoc> byEmail = searchMemberEsRepository.findByEmail("new@new");
		searchMemberEsRepository.deleteAll(byEmail);

		List<SubscribeDoc> bySubscribeMemberEmail = subscribeEsRepository.findBySubscribeMemberEmail("new@new");
		List<SubscribeDoc> bySubscribeMemberEmail2 = subscribeEsRepository.findBySubscribeMemberEmail("new2@new");

		subscribeEsRepository.deleteAll(bySubscribeMemberEmail);
		subscribeEsRepository.deleteAll(bySubscribeMemberEmail2);
	}
}
