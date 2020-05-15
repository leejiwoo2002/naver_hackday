package com.hackday.sns_timeline.memberSearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.domain.entity.SubscribePK;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberSearchService {

	final private MemberRepository memberRepository;
	final private SubscribeRepository subscribeRepository;

	public Page<MemberDto> findMembers(String search, Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 10);
		Page<MemberDto> searchMembers = memberRepository.searchMember(search, pageable).map(MemberDto::customConverter);

		return searchMembers;
	}

	public void checkSubscribed(Page<MemberDto> searchMembers, long id){
		for (MemberDto searchMember : searchMembers) {
			if(subscribeRepository.findById(SubscribePK.builder().userId(id)
				.subscribeTargetId(searchMember.getId()).build()).isPresent()){
				searchMember.setSubscribed(true);
			}
		}
	}

	public RedirectAttributes setMemberSearchAttributes(RedirectAttributes redirectAttributes, Page<MemberDto> memberDtoList){

		int start = (int) Math.floor(memberDtoList.getNumber()/10)*10 + 1;
		int last = start + 9 < memberDtoList.getTotalPages() ? start + 9 : memberDtoList.getTotalPages();
		redirectAttributes.addFlashAttribute(CommonConst.START, start);
		redirectAttributes.addFlashAttribute(CommonConst.LAST, last);
		redirectAttributes.addFlashAttribute(CommonConst.MEMBER_DTO_LIST, memberDtoList);

		return redirectAttributes;
	}
}
