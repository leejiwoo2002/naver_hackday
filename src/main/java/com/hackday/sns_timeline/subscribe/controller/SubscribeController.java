package com.hackday.sns_timeline.subscribe.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.memberSearch.service.MemberSearchService;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.subscribe.domain.entity.Subscribe;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscribeController {

	final private SubscribeService subscribeService;
	final private MemberSearchService memberSearchService;

	@PostMapping()
	public @ResponseBody  String addSubscribe(@ModelAttribute("subscribeDto") @Valid SubscribeDto subscribeDto,
		RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUser user) throws Exception {

		if(user == null){
			return "redirect:/";
		}

		log.info(subscribeDto.getId());
		log.info(subscribeDto.getPage());
		log.info(subscribeDto.getSearch());

		Page<MemberDto> memberDtoList = memberSearchService.findMembers(subscribeDto.getSearch(),
			PageRequest.of(subscribeDto.getPage(), 10));

		if(memberDtoList.getContent().size() > 0) {
			memberSearchService.checkSubscribed(memberDtoList, user.getId());
			redirectAttributes.addFlashAttribute(CommonConst.CONTENT_DTO_LIST, memberDtoList);
		}

		Subscribe subscribe = subscribeService.addSubscribe(subscribeDto.getId(), user.getId());

		// if(subscribe.getSubscribePK().getSubscriberId() != user.getId() ||
		// 	subscribe.getSubscribePK().getSubscribedMemberId() != subscribeDto.getId()){
		// 	throw new Exception();
		// }

		return "redirect:/member/search";
	}
}
