package com.hackday.sns_timeline.profile.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProfileService {

	final private MemberRepository memberRepository;

	final private SubscribeRepository subscribeRepository;

	@Transactional
	public Member getMemberProfile(long userId) throws Exception {
		return memberRepository.findById(userId).orElseThrow(() -> new Exception());
	}

	@Transactional
	public List<MemberDto> getSubscribeMember(Member member){
		List<Subscribe> subscribeList = subscribeRepository.findByMember(member);

		List<MemberDto> memberDtoList = new ArrayList<>();
		for (Subscribe subscribe : subscribeList) {
			memberDtoList.add(MemberDto.customConverter(subscribe.getSubscribeMember()));
		}

		return memberDtoList;
	}
}
