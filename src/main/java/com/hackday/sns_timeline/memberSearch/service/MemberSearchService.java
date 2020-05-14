package com.hackday.sns_timeline.memberSearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberSearchService {

	final private MemberRepository memberRepository;

	public Page<MemberDto> findMembers(String search, Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 10);
		Page<MemberDto> searchMembers = memberRepository.searchMember(search, pageable).map(MemberDto::customConverter);

		return searchMembers;
	}
}
