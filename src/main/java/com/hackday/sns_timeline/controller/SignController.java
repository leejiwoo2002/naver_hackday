package com.hackday.sns_timeline.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.domain.dto.CustomUser;
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

	@GetMapping("/up")
	public ModelAndView getSignUpPage(@ModelAttribute MemberDto memberDto, HttpServletRequest request) {
		return new ModelAndView("signUp");
	}

	@PostMapping("/up/do")
	public String signUp(@Valid MemberDto memberDto) throws Exception {
		log.info(memberDto.getEmail() + " tries to sign up");

		signService.signUp(memberDto);

		log.info(memberDto.getEmail() + " succeeds to log up");
		return "redirect:/";
	}

	@GetMapping("/in")
	public String signInSuccess(@AuthenticationPrincipal CustomUser user) {
		log.info(user.getUsername());

		return "redirect:/timeLine";
	}
}
