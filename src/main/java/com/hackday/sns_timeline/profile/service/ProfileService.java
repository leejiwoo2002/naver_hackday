package com.hackday.sns_timeline.profile.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.domain.entity.SubscribeEs;
import com.hackday.sns_timeline.subscribe.repository.SubscribeEsRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProfileService {

	final private MemberRepository memberRepository;

	final private SubscribeRepository subscribeRepository;

	final private SubscribeEsRepository subscribeEsRepository;

	@Transactional
	public Member getMemberProfile(long userId) throws Exception {
		return memberRepository.findById(userId).orElseThrow(() -> new Exception("member not exist"));
	}

	@Transactional
	public List<MemberDto> getSubscribeMember(Member member){
		List<SubscribeEs> subscribeEsList = subscribeEsRepository.findByMemberId(member.getId());

		List<MemberDto> memberDtoList = new ArrayList<>();
		for (SubscribeEs subscribeEs : subscribeEsList) {
			memberDtoList.add(MemberDto.subscribeEsConverter(subscribeEs));
		}

		return memberDtoList;
	}
}
