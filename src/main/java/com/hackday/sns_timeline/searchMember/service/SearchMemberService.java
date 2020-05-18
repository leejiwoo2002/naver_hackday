package com.hackday.sns_timeline.searchMember.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.common.CommonFunction;
import com.hackday.sns_timeline.searchMember.domain.dto.SearchMemberDto;
import com.hackday.sns_timeline.searchMember.domain.entity.SearchMember;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberRepository;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SearchMemberService {

	final private MemberRepository memberRepository;
	final private SubscribeRepository subscribeRepository;
	final private SearchMemberRepository searchMemberRepository;

	@Transactional
	public Page<MemberDto> findMembers(String search, int page) {
		page = (page == 0) ? 0 : (page - 1);
		Pageable pageable = PageRequest.of(page, 10);
		Page<MemberDto> searchMembers = memberRepository.searchMember(search, pageable).map(MemberDto::customConverter);

		return searchMembers;
	}

	@Transactional
	public void checkSubscribed(Page<MemberDto> searchMembers, long id) throws Exception {
		Member member = memberRepository.findById(id).orElseThrow(() -> new Exception());
		List<Member> subscribeList = subscribeRepository.findSubscribeIdByMember(member);
		Set<Long> subscribeMemberIdSet = new HashSet<>();
		for (Member currentMember : subscribeList) {
			subscribeMemberIdSet.add(currentMember.getId());
		}

		for (MemberDto searchMember : searchMembers) {
			if(subscribeMemberIdSet.contains(searchMember.getId())){
				searchMember.setSubscribed(true);
			}
		}
	}

	public RedirectAttributes setMemberSearchAttributes(RedirectAttributes redirectAttributes,
		SearchMemberDto searchMemberDto) throws Exception {

		Page<MemberDto> memberDtoList = findMembers(searchMemberDto.getSearch(), searchMemberDto.getPage());

		redirectAttributes.addFlashAttribute(CommonConst.SEARCH, searchMemberDto.getSearch());

		if(memberDtoList.getContent().size() > 0) {
			checkSubscribed(memberDtoList, searchMemberDto.getUserId());
			int start = CommonFunction.getStartPageNumber(memberDtoList.getNumber());
			int last = CommonFunction.getLastPageNumber(start, memberDtoList.getTotalPages());
			redirectAttributes.addFlashAttribute(CommonConst.START, start);
			redirectAttributes.addFlashAttribute(CommonConst.LAST, last);
			redirectAttributes.addFlashAttribute(CommonConst.MEMBER_DTO_LIST, memberDtoList);
		}else {
			redirectAttributes.addFlashAttribute(CommonConst.IS_NULL, true);
		}

		return redirectAttributes;
	}

	public List<SearchMember> findSearchMemberList(String name){
		return searchMemberRepository.findByName(name);
	}

	public List<SearchMember> fineSearchMemberByEmailLike(String email){
		return searchMemberRepository.findByEmailContains(email);
	}

	public SearchMember saveSearchMember(SearchMember memberSearch){
		return searchMemberRepository.save(memberSearch);
	}


}
