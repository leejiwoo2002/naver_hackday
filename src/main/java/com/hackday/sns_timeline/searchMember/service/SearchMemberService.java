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
	public Page<MemberDto> findMembers(String search, Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 10);
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

	public RedirectAttributes setMemberSearchAttributes(RedirectAttributes redirectAttributes, Page<MemberDto> memberDtoList){

		int start = (int) Math.floor(memberDtoList.getNumber()/10)*10 + 1; // 상수
		int last = start + 9 < memberDtoList.getTotalPages() ? start + 9 : memberDtoList.getTotalPages();
		redirectAttributes.addFlashAttribute(CommonConst.START, start);
		redirectAttributes.addFlashAttribute(CommonConst.LAST, last);
		redirectAttributes.addFlashAttribute(CommonConst.MEMBER_DTO_LIST, memberDtoList);

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
