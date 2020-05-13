package com.hackday.sns_timeline.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.domain.dto.MemberDto;
import com.hackday.sns_timeline.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.domain.entity.Member;
import com.hackday.sns_timeline.service.MemberSearchService;
import com.hackday.sns_timeline.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member/search")
public class MemberSearchController {

	final private MemberSearchService memberSearchService;
	final private SignService signService;

	@GetMapping
	public ModelAndView searchMemberPage(@ModelAttribute SubscribeDto subscribeDto) {
		log.info("call search member");
		return new ModelAndView("searchMember");
	}

	@GetMapping("/do")
	public ModelAndView searchMember(@RequestParam(name = "search") String search,
		@PageableDefault Pageable pageable) {
		log.info("search = " + search);
		Page<MemberDto> memberDtoList = memberSearchService.findMembers(search, pageable);
		return new ModelAndView("searchMember").addObject(CommonConst.MEMBER_DTO_LIST, memberDtoList);
	}

	@GetMapping("/test")
	public @ResponseBody String createTestData(@RequestParam(name = "name") String name,
												@RequestParam(name = "count") int count) throws Exception{

		for (int i = 1; i <= count; i++) {
			signService.signUp(MemberDto.builder()
				.email(name+i + "@"+name)
				.name(name+i)
				.password("test")
				.build());
		}

		return "OK";
	}
}
