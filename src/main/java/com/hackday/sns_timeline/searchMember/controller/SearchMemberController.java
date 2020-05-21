package com.hackday.sns_timeline.searchMember.controller;

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
import com.hackday.sns_timeline.common.commonEnum.PAGE;
import com.hackday.sns_timeline.common.commonEnum.REDIRECT;
import com.hackday.sns_timeline.searchMember.domain.dto.SearchMemberDto;
import com.hackday.sns_timeline.searchMember.service.SearchMemberService;
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
@RequestMapping("/search/member")
@Api(value = "/search/member", description = "친구찾기 기능 담당")
public class SearchMemberController {

	final private SearchMemberService searchMemberService;

	@ApiOperation(
		httpMethod = "GET",
		value = "친구 찾기 페이지",
		response = ModelAndView.class,
		nickname="searchMemberPage"
	)
	@GetMapping
	public ModelAndView searchMemberPage(@ModelAttribute SubscribeDto subscribeDto) {
		return new ModelAndView(PAGE.SEARCH_MEMBER.getPage());
	}

	@ApiOperation(
		httpMethod = "GET",
		value = "친구 찾기 Page<MemberDto>(요청 된 페이지) 반환",
		response = String.class,
		nickname="searchMember"
	)
	@GetMapping("/do")
	public String searchMember(@RequestParam(name = "search") String search, @PageableDefault Pageable pageable,
		RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUser user) throws Exception {
		if(user == null) {
			return REDIRECT.INDEX.getRedirectUrl();
		}

		searchMemberService.setRedirectAttributes(redirectAttributes,
			SearchMemberDto.builder()
				.search(search)
				.page(pageable.getPageNumber())
				.userId(user.getId())
				.build());

		return REDIRECT.SEARCH_MEMBER.getRedirectUrl();
	}


	// @ApiOperation(
	// 	httpMethod = "GET",
	// 	value = "Mock 데이터를 넣기 위한 end point",
	// 	response = String.class,
	// 	nickname="createTestData"
	// )
	// @GetMapping("/test")
	// public String createTestData(@RequestParam(name = "name") String name,
	// 	@RequestParam(name = "count") int count) throws Exception {
	//
	// 	for (int i = 1; i <= count; i++) {
	// 		signService.signUp(MemberDto.builder()
	// 			.email(name+i + "@"+name)
	// 			.name(name+i)
	// 			.password("test")
	// 			.build());
	// 	}
	//
	// 	return "redirect:/search/member";
	// }
}
