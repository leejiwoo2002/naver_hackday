package com.hackday.sns_timeline.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.domain.dto.MemberDto;
import com.hackday.sns_timeline.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

	final private SignService signService;

	@RequestMapping(value = "/up", method = RequestMethod.GET)
	public ModelAndView getSignUpPage(@ModelAttribute MemberDto memberDto) {
		return new ModelAndView("signUp").addObject(CommonConst.MEMBER_DTO, memberDto);
	}

	@RequestMapping(value = "/up/do", method = RequestMethod.POST)
	public ModelAndView signUp(@ModelAttribute(CommonConst.MEMBER_DTO) @Valid MemberDto memberDto) throws Exception {
		log.info(memberDto.getEmail() + " tries to sign up");

		signService.signUp(memberDto);


		log.info(memberDto.getEmail() + " succeeds to log up");
		return new ModelAndView("index").addObject(CommonConst.MEMBER_DTO, new MemberDto());
	}

	@RequestMapping(value = "/in", method = RequestMethod.GET)
	public ModelAndView signInSuccess(@AuthenticationPrincipal User user) {
		log.info(user.getUsername());

		return new ModelAndView("timeLine");
	}
}
