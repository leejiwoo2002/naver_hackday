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
import com.hackday.sns_timeline.searchMember.domain.entity.SearchMemberEs;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberEsRepository;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.domain.entity.Member;
import com.hackday.sns_timeline.sign.repository.MemberRepository;
import com.hackday.sns_timeline.subscribe.domain.entity.SubscribeEs;
import com.hackday.sns_timeline.subscribe.repository.SubscribeEsRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SearchMemberService {

	final private SearchMemberEsRepository searchMemberEsRepository;
	final private SubscribeEsRepository subscribeEsRepository;

	@Transactional
	public Page<MemberDto> findMembers(String search, int page) {
		page = (page == 0) ? 0 : (page - 1);
		Pageable pageable = PageRequest.of(page, 10);
		Page<MemberDto> searchMembers = searchMemberEsRepository.findByEmailContainsOrNameContains(search, search, pageable)
			.map(MemberDto::searchMemberEsConverter);

		return searchMembers;
	}

	@Transactional
	public void checkSubscribed(Page<MemberDto> searchMembers, long id) throws Exception {
		SearchMemberEs searchMemberEs = searchMemberEsRepository.findByMemberId(id).orElseThrow(() -> new Exception());
		List<SubscribeEs> subscribeList = subscribeEsRepository.findByMemberId(searchMemberEs.getMemberId());
		Set<Long> subscribeMemberIdSet = new HashSet<>();

		for (SubscribeEs current : subscribeList) {
			subscribeMemberIdSet.add(current.getSubscribeMemberId());
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

	public List<SearchMemberEs> findSearchMemberList(String name){
		return searchMemberEsRepository.findByName(name);
	}

	public Page<SearchMemberEs> fineSearchMemberByEmailLikeOrNameLike(String email){
		return searchMemberEsRepository.findByEmailContainsOrNameContains(email, email, PageRequest.of(0, 10));
	}

	public SearchMemberEs saveSearchMember(SearchMemberEs memberSearch){
		return searchMemberEsRepository.save(memberSearch);
	}


}
