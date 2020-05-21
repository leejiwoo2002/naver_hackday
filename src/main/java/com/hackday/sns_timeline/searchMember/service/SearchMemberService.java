package com.hackday.sns_timeline.searchMember.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonFunction;
import com.hackday.sns_timeline.common.commonEnum.ATTRIBUTE;
import com.hackday.sns_timeline.error.customException.RepositoryNullException;
import com.hackday.sns_timeline.searchMember.domain.dto.SearchMemberDto;
import com.hackday.sns_timeline.searchMember.domain.document.SearchMemberDoc;
import com.hackday.sns_timeline.searchMember.repository.SearchMemberDocRepository;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.subscribe.domain.document.SubscribeDoc;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.repository.SubscribeDocRepository;
import com.hackday.sns_timeline.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class SearchMemberService {

	final private SearchMemberDocRepository searchMemberDocRepository;
	final private SubscribeDocRepository subscribeDocRepository;

	@Transactional
	public Page<MemberDto> findMembers(String search, int page) {
		page = (page == 0) ? 0 : (page - 1);
		Pageable pageable = PageRequest.of(page, 10);
		Page<MemberDto> searchMembers = searchMemberDocRepository.findByEmailContainsOrNameContains(search, search, pageable)
			.map(MemberDto::searchMemberEsConverter);

		return searchMembers;
	}

	@Transactional
	public void checkSubscribed(Page<MemberDto> searchMembers, long id) throws RepositoryNullException {
		SearchMemberDoc searchMemberDoc = searchMemberDocRepository.findByMemberId(id)
			.orElseThrow(() -> new RepositoryNullException("searchMemberDocRepository error"));

		List<SubscribeDoc> subscribeDocList = subscribeDocRepository.findByMemberId(searchMemberDoc.getMemberId());
		Set<Long> subscribeMemberIdSet = subscribeDocList.stream().map(SubscribeDoc::getSubscribeMemberId)
			.collect(Collectors.toSet());

		searchMembers.stream().filter(item -> subscribeMemberIdSet.contains(item.getId())).forEach(item -> item.setSubscribed(true));
	}

	public RedirectAttributes setRedirectAttributes(RedirectAttributes redirectAttributes,
		SearchMemberDto searchMemberDto) {

		Page<MemberDto> memberDtoList = findMembers(searchMemberDto.getSearch(), searchMemberDto.getPage());

		redirectAttributes.addFlashAttribute(ATTRIBUTE.SEARCH.getName(), searchMemberDto.getSearch());

		if(memberDtoList.getContent().size() > 0) {
			checkSubscribed(memberDtoList, searchMemberDto.getUserId());
			int start = CommonFunction.getStartPageNumber(memberDtoList.getNumber());
			int last = CommonFunction.getLastPageNumber(start, memberDtoList.getTotalPages());
			redirectAttributes.addFlashAttribute(ATTRIBUTE.START.getName(), start);
			redirectAttributes.addFlashAttribute(ATTRIBUTE.LAST.getName(), last);
			redirectAttributes.addFlashAttribute(ATTRIBUTE.MEMBER_DTO_LIST.getName(), memberDtoList);
		}else {
			redirectAttributes.addFlashAttribute(ATTRIBUTE.IS_NULL.getName(), true);
		}

		return redirectAttributes;
	}

	public Page<SearchMemberDoc> fineSearchMemberByEmailLikeOrNameLike(String email){
		return searchMemberDocRepository.findByEmailContainsOrNameContains(email, email, PageRequest.of(0, 10));
	}

	public SearchMemberDoc saveSearchMember(SearchMemberDoc memberSearch){
		return searchMemberDocRepository.save(memberSearch);
	}
}
