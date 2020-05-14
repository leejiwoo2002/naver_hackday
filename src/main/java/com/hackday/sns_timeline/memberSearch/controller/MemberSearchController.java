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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.memberSearch.service.MemberSearchService;
import com.hackday.sns_timeline.sign.service.SignService;
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
		return new ModelAndView("searchMember");
	}

	@GetMapping("/do")
	public String searchMember(@RequestParam(name = "search") String search, @PageableDefault Pageable pageable,
		RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUser user) {
		if(user == null) {
			return "redirect:/";
		}

		redirectAttributes.addFlashAttribute("search", search);

		Page<MemberDto> memberDtoList = memberSearchService.findMembers(search, pageable);

		if(memberDtoList.getContent().size() == 0){
			redirectAttributes.addFlashAttribute("isNull", true);
		} else {
			memberSearchService.checkSubscribed(memberDtoList, user.getId());
			int start = (int) Math.floor(memberDtoList.getNumber()/10)*10 + 1;
			int last = start + 9 < memberDtoList.getTotalPages() ? start + 9 : memberDtoList.getTotalPages();
			redirectAttributes.addFlashAttribute("start", start);
			redirectAttributes.addFlashAttribute("last", last);
			redirectAttributes.addFlashAttribute(CommonConst.MEMBER_DTO_LIST, memberDtoList);
		}

		return "redirect:/member/search";
	}

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
