package com.hackday.sns_timeline.subscribe.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SubscribeService {

	final private MemberRepository memberRepository;
	final private SubscribeRepository subscribeRepository;
	final private SubscribeEsRepository subscribeEsRepository;

	@Transactional
	public Subscribe addSubscribe(long userId, long subscribeTargetId) throws Exception{
		Member member = memberRepository.findById(userId).orElseThrow(() -> new Exception("member not exist"));
		Member subscribeMember = memberRepository.findById(subscribeTargetId)
			.orElseThrow(() -> new Exception("member not exist"));

		addSubscribeEs(member, subscribeMember);
		return subscribeRepository.save(Subscribe.buildSubscribe(member, subscribeMember));
	}

	@Transactional
	public void deleteSubscribe(long userId, long subscribeTargetId) throws Exception {
		Member member = memberRepository.findById(userId).orElseThrow(() -> new Exception("member not exist"));
		Member subscribeMember = memberRepository.findById(subscribeTargetId)
			.orElseThrow(() -> new Exception("member not exist"));
		Subscribe subscribe = subscribeRepository.findByMemberAndSubscribeMember(member, subscribeMember)
			.orElseThrow(() -> new Exception("subscribe not exist"));

		subscribeRepository.delete(subscribe);
		deleteSubscribeEs(member, subscribeMember);
	}

	@Transactional
	public SubscribeEs addSubscribeEs(Member member, Member subscribeMember){
		return subscribeEsRepository.save(SubscribeEs.buildSubscribeEs(member, subscribeMember));
	}

	@Transactional
	public void deleteSubscribeEs(Member member, Member subscribeMember) throws Exception {
		SubscribeEs subscribeEs = subscribeEsRepository.findByMemberIdAndSubscribeMemberId
			(member.getId(), subscribeMember.getId()).orElseThrow(() -> new Exception("subscribe is not exist"));

		subscribeEsRepository.delete(subscribeEs);
	}
}
