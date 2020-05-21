package com.hackday.sns_timeline.subscribe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackday.sns_timeline.common.CommonFunction;
import com.hackday.sns_timeline.error.customException.RepositoryNullException;
import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberDocRepository;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
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

	private final MemberRepository memberRepository;
	final private SearchMemberDocRepository searchMemberDocRepository;
	final private SubscribeRepository subscribeRepository;
	final private SubscribeDocRepository subscribeDocRepository;

	@Transactional
	public Subscribe addSubscribe(long userId, long subscribeTargetId) throws RepositoryNullException {
		SearchMemberDoc member = searchMemberDocRepository.findByMemberId(userId)
			.orElseThrow(() -> new RepositoryNullException("searchMemberDocRepository error"));

		SearchMemberDoc subscribeMember = searchMemberDocRepository.findByMemberId(subscribeTargetId)
			.orElseThrow(() -> new RepositoryNullException("searchMemberDocRepository error"));

		subscribeDocRepository.save(SubscribeDoc.buildSubscribeEs(member, subscribeMember));
		return subscribeRepository.save(Subscribe.builder()
			.memberId(member.getMemberId()).subscribeMemberId(subscribeMember.getMemberId())
			.regDate(CommonFunction.getCurrentDate()).build());
	}

	@Transactional
	public void deleteSubscribe(long userId, long subscribeTargetId) throws RepositoryNullException {
		Subscribe subscribe = subscribeRepository.findByMemberIdAndSubscribeMemberId(userId, subscribeTargetId)
			.orElseThrow(() -> new RepositoryNullException("subscribeRepository error"));

		subscribeRepository.delete(subscribe);
		SubscribeDoc subscribeDoc = subscribeDocRepository.findByMemberIdAndSubscribeMemberId
			(userId, subscribeTargetId).orElseThrow(() -> new RepositoryNullException("subscribeRepository error"));

		subscribeDocRepository.delete(subscribeDoc);
	}
}
