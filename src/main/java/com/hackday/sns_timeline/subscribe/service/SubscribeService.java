package com.hackday.sns_timeline.subscribe.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.entity.SearchMemberEs;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberEsRepository;
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

	final private SearchMemberEsRepository searchMemberEsRepository;
	final private SubscribeRepository subscribeRepository;
	final private SubscribeEsRepository subscribeEsRepository;

	@Transactional
	public Subscribe addSubscribe(long userId, long subscribeTargetId) throws Exception{
		SearchMemberEs member = searchMemberEsRepository.findByMemberId(userId)
			.orElseThrow(() -> new Exception("member not exist"));

		SearchMemberEs subscribeMember = searchMemberEsRepository.findByMemberId(subscribeTargetId)
			.orElseThrow(() -> new Exception("member not exist"));

		addSubscribeEs(member, subscribeMember);
		return subscribeRepository.save(Subscribe.buildSubscribe(member.getMemberId(), subscribeMember.getMemberId()));
	}

	@Transactional
	public void deleteSubscribe(long userId, long subscribeTargetId) throws Exception {
		Subscribe subscribe = subscribeRepository.findByMemberIdAndSubscribeMemberId(userId, subscribeTargetId)
			.orElseThrow(() -> new Exception("subscribe not exist"));

		subscribeRepository.delete(subscribe);
		deleteSubscribeEs(userId, subscribeTargetId);
	}

	@Transactional
	public SubscribeEs addSubscribeEs(SearchMemberEs member, SearchMemberEs subscribeMember){
		return subscribeEsRepository.save(SubscribeEs.buildSubscribeEs(member, subscribeMember));
	}

	@Transactional
	public void deleteSubscribeEs(long memberId, long subscribeMemberId) throws Exception {
		SubscribeEs subscribeEs = subscribeEsRepository.findByMemberIdAndSubscribeMemberId
			(memberId, subscribeMemberId).orElseThrow(() -> new Exception("subscribe is not exist"));

		subscribeEsRepository.delete(subscribeEs);
	}
}
