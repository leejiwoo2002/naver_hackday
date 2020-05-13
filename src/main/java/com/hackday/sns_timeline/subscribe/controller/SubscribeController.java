package com.hackday.sns_timeline.subscribe.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.subscribe.domain.dto.SubscribeDto;
import com.hackday.sns_timeline.subscribe.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/subscribe")
public class SubscribeController {

	final private SubscribeService subscribeService;

	@PostMapping("/do")
	public String addSubscribe(@ModelAttribute("subscribeDto") @Valid SubscribeDto subscribeDto, @AuthenticationPrincipal CustomUser user) throws Exception {

		log.info(subscribeDto.getId());

		subscribeService.addSubscribe(subscribeDto.getId(), user.getId());

		return "redirect:/member/search";
	}
}
