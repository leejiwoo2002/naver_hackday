package com.hackday.sns_timeline.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberSearchService {

	final private MemberRepository memberRepository;
	final private SignService signService;

	public Page<Member> findMembers(String search, Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 10);
		Page<Member> searchMembers = memberRepository.searchMember(search, pageable);
		return searchMembers;
	}

	public void addSubscribe(Long targetId, String email) throws Exception {
		Member byEmail = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("can't find email"));

		Member target = memberRepository.findById(targetId).orElseThrow(() -> new Exception("can't find email"));

		if (!byEmail.getSubscribe().contains(target)) {
			log.info("add");
			byEmail.getSubscribe().add(target);
			memberRepository.save(byEmail);
		} else {
			log.info("already subscribe");
		}
	}
}
