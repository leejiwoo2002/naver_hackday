package com.hackday.sns_timeline.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.domain.entity.Subscribe;
import com.hackday.sns_timeline.domain.entity.SubscribePK;
import com.hackday.sns_timeline.repository.MemberRepository;
import com.hackday.sns_timeline.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscribeService {

	final private MemberRepository memberRepository;
	final private SubscribeRepository subscribeRepository;

	public void addSubscribe(String subscribeEmail, String subscribedEmail) throws Exception{
		Member subscribeMember = memberRepository.findByEmail(subscribeEmail).orElseThrow(() -> new Exception());
		Member subscribedMember = memberRepository.findByEmail(subscribedEmail).orElseThrow(() -> new Exception());

		saveSubscribe(subscribeMember, subscribedMember);
	}

	public void saveSubscribe(Member subscribeMember, Member subscribedMember){
		LocalDateTime currentDateTime = LocalDateTime.now();
		Date date = java.sql.Timestamp.valueOf(currentDateTime.plusHours(9));

		SubscribePK subscribePK = SubscribePK.builder().subscriberId(subscribeMember.getId())
			.subscribedMemberId(subscribedMember.getId()).build();

		Subscribe subscribe = Subscribe.builder().subscribePK(subscribePK).regDate(date).build();

		subscribeRepository.save(subscribe);
	}

}
