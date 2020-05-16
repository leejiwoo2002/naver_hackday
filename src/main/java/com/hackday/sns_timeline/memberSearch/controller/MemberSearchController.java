package com.hackday.sns_timeline.memberSearch.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.memberSearch.service.MemberSearchService;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.service.SignService;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member/search")
@Api(value = "/member/search", description = "친구찾기 기능 담당")
public class MemberSearchController {

	final private MemberSearchService memberSearchService;
	final private SignService signService;

	@ApiOperation(
		httpMethod = "GET",
		value = "친구 찾기 페이지",
		response = ModelAndView.class,
		nickname="searchMemberPage"
	)
	@GetMapping
	public ModelAndView searchMemberPage(@ModelAttribute SubscribeDto subscribeDto) {
		return new ModelAndView(CommonConst.SEARCH_MEMBER);
	}

	@ApiOperation(
		httpMethod = "GET",
		value = "친구 찾기 Page<MemberDto>(요청 된 페이지) 반환",
		response = String.class,
		nickname="searchMember"
	)
	@GetMapping("/do")
	public String searchMember(@RequestParam(name = CommonConst.SEARCH) String search, @PageableDefault Pageable pageable,
		RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUser user) throws Exception {
		if(user == null) {
			return CommonConst.REDIRECT_INDEX;
		}

		redirectAttributes.addFlashAttribute(CommonConst.SEARCH, search);

		Page<MemberDto> memberDtoList = memberSearchService.findMembers(search, pageable);

		if(memberDtoList.getContent().size() == 0){
			redirectAttributes.addFlashAttribute(CommonConst.IS_NULL, true);
		} else {
			memberSearchService.checkSubscribed(memberDtoList, user.getId());
			memberSearchService.setMemberSearchAttributes(redirectAttributes, memberDtoList);
		}

		return CommonConst.REDIRECT_MEMBER_SEARCH;
	}


	@ApiOperation(
		httpMethod = "GET",
		value = "Mock 데이터를 넣기 위한 end point",
		response = String.class,
		nickname="createTestData"
	)
	@GetMapping("/test")
	public String createTestData(@RequestParam(name = "name") String name,
		@RequestParam(name = "count") int count) throws Exception {

		for (int i = 1; i <= count; i++) {
			signService.signUp(MemberDto.builder()
				.email(name+i + "@"+name)
				.name(name+i)
				.password("test")
				.build());
		}

		return "redirect:/member/search";
	}
}
