package com.hackday.sns_timeline.profile.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {



	@GetMapping
	public ModelAndView getProfilePage(@AuthenticationPrincipal CustomUser user){
		if(user == null){
			return new ModelAndView(CommonConst.TIME_LINE);
		}
		ModelAndView modelAndView = new ModelAndView(CommonConst.PROFILE);

		return modelAndView;
	}
}
