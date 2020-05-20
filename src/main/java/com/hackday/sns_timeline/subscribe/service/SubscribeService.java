package com.hackday.sns_timeline.subscribe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberEsRepository;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.domain.document.SubscribeDoc;
import com.hackday.sns_timeline.subscribe.repository.SubscribeDocRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscribeService {

	final private SearchMemberEsRepository searchMemberEsRepository;
	final private SubscribeRepository subscribeRepository;
	final private SubscribeDocRepository subscribeDocRepository;

	@Transactional
	public Subscribe addSubscribe(long userId, long subscribeTargetId) throws Exception{
		SearchMemberDoc member = searchMemberEsRepository.findByMemberId(userId)
			.orElseThrow(() -> new Exception("member not exist"));

		SearchMemberDoc subscribeMember = searchMemberEsRepository.findByMemberId(subscribeTargetId)
			.orElseThrow(() -> new Exception("member not exist"));

		subscribeDocRepository.save(SubscribeDoc.buildSubscribeEs(member, subscribeMember));
		return subscribeRepository.save(Subscribe.buildSubscribe(member.getMemberId(), subscribeMember.getMemberId()));
	}

	@Transactional
	public void deleteSubscribe(long userId, long subscribeTargetId) throws Exception {
		Subscribe subscribe = subscribeRepository.findByMemberIdAndSubscribeMemberId(userId, subscribeTargetId)
			.orElseThrow(() -> new Exception("subscribe not exist"));

		subscribeRepository.delete(subscribe);
		SubscribeDoc subscribeDoc = subscribeDocRepository.findByMemberIdAndSubscribeMemberId
			(userId, subscribeTargetId).orElseThrow(() -> new Exception("subscribe is not exist"));

		subscribeDocRepository.delete(subscribeDoc);
	}
}
