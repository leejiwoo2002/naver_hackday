package com.hackday.sns_timeline.subscribe.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.domain.entity.SubscribePK;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscribeService {

	final private MemberRepository memberRepository;
	final private SubscribeRepository subscribeRepository;

	public Subscribe addSubscribe(long subscribeId, long subscribedId) throws Exception{
		Member subscribeMember = memberRepository.findById(subscribeId).orElseThrow(() -> new Exception());
		Member subscribedMember = memberRepository.findById(subscribedId).orElseThrow(() -> new Exception());

		return saveSubscribe(subscribeMember, subscribedMember);
	}

	public Subscribe saveSubscribe(Member subscribeMember, Member subscribedMember){
		LocalDateTime currentDateTime = LocalDateTime.now();
		Date date = java.sql.Timestamp.valueOf(currentDateTime.plusHours(9));

		SubscribePK subscribePK = SubscribePK.builder().subscriberId(subscribeMember.getId())
			.subscribedMemberId(subscribedMember.getId()).build();

		Subscribe subscribe = subscribeRepository.findById(subscribePK).orElseGet(() -> new Subscribe());

		if(subscribe.getSubscribePK() != null) {
			return subscribe;
		}

		subscribe = Subscribe.builder().subscribePK(subscribePK).regDate(date).build();

		return subscribeRepository.save(subscribe);
	}
}