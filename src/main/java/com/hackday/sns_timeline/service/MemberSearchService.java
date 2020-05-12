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

	public Page<Member> findMembers(String search, Pageable pageable) {
		// pageable 에서는 시작페이지가 0 이라서 0번째 페이지를 찾는게 아니면 1을 빼주고 검색
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		// pageable 객체 커스텀
		pageable = PageRequest.of(page, 10);
		// 페이지 객체로 찾아옴
		Page<Member> searchMembers = memberRepository.searchMember(search, pageable);
		return searchMembers;
	}
}
