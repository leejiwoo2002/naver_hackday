package com.hackday.sns_timeline.subscribe.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.common.commonEnum.REDIRECT;
import com.hackday.sns_timeline.searchMember.domain.dto.SearchMemberDto;
import com.hackday.sns_timeline.searchMember.service.SearchMemberService;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/subscribe")
@Api(value = "/subscribe", description = "구독 기능 관리")
public class SubscribeController {

	final private SubscribeService subscribeService;
	final private SearchMemberService searchMemberService;

	@ApiOperation(
		httpMethod = "POST",
		value = "구독 추가 / 삭제 기능, param 으로 오는 Boolean 값으로 추가, 삭제 결정",
		response = String.class,
		nickname="manageSubscribe"
	)
	@PostMapping()
	public String manageSubscribe(@ModelAttribute(CommonConst.SUBSCRIBE_DTO) @Valid SubscribeDto subscribeDto,
		RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUser user) throws Exception {

		if(user == null){
			return REDIRECT.INDEX.getRedirectUrl();
		}

		if(subscribeDto.getSubscribed()){
			subscribeService.deleteSubscribe(user.getId(), subscribeDto.getId());
		} else {
			subscribeService.addSubscribe(user.getId(), subscribeDto.getId());
		}

		searchMemberService.setRedirectAttributes(redirectAttributes,
			SearchMemberDto.builder()
				.search(subscribeDto.getSearch())
				.page(subscribeDto.getPage())
				.userId(user.getId())
				.build());

		return REDIRECT.SEARCH_MEMBER.getRedirectUrl();
	}
}
