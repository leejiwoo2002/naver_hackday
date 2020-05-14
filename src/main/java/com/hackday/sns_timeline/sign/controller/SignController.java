package com.hackday.sns_timeline.sign.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

	final private SignService signService;

	@GetMapping("/up")
	public ModelAndView getSignUpPage(@ModelAttribute MemberDto memberDto) {
		return new ModelAndView("signUp");
	}

	@PostMapping("/up")
	public String signUp(@Valid MemberDto memberDto) throws Exception {
		signService.signUp(memberDto);
		return "redirect:/";
	}

	@GetMapping("/in")
	public String signInSuccess(@AuthenticationPrincipal CustomUser user) {
		if(user==null) {
			return "redirect:/";
		}
		return "redirect:/timeLine";
	}

	@GetMapping("/fail")
	public String signInFail(RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("error", true);
		return "redirect:/";
	}
}
