package com.hackday.sns_timeline.subscribe.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public Subscribe addSubscribe(long userId, long subscribeTargetId) throws Exception{
		LocalDateTime currentDateTime = LocalDateTime.now();
		Date date = java.sql.Timestamp.valueOf(currentDateTime.plusHours(9));

		Subscribe subscribe = getSubscribe(userId, subscribeTargetId).orElseGet(() -> new Subscribe());

		if(subscribe.getSubscribePK() != null) {
			log.info("already subscribe");
			log.info(subscribe.getSubscribePK().getUserId());
			log.info(subscribe.getSubscribePK().getSubscribeTargetId());
			return subscribe;
		}

		subscribe = Subscribe.builder().subscribePK(SubscribePK.builder().userId(userId)
			.subscribeTargetId(subscribeTargetId).build()).regDate(date).build();

		return subscribeRepository.save(subscribe);
	}

	@Transactional
	public void deleteSubscribe(long userId, long subscribeTargetId) throws Exception {
		Subscribe subscribe = getSubscribe(userId, subscribeTargetId).orElseThrow(() -> new Exception("subscribe not exist"));

		subscribeRepository.delete(subscribe);
	}

	private Optional<Subscribe> getSubscribe(long userId, long subscribeTargetId) throws Exception {
		Member userMember = memberRepository.findById(userId).orElseThrow(() -> new Exception("member not present"));
		Member subscribeTargetMember = memberRepository.findById(subscribeTargetId).orElseThrow(()
			-> new Exception("member not present"));

		SubscribePK subscribePK = SubscribePK.builder().userId(userMember.getId())
			.subscribeTargetId(subscribeTargetMember.getId()).build();

		return subscribeRepository.findById(subscribePK);
	}
}
