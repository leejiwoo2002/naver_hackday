package com.hackday.sns_timeline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

	// @RequestMapping(value = "/up", method = RequestMethod.GET)
	// public ModelAndView signUp(@ModelAttribute MemberDto memberDto) {
	// 	return new ModelAndView("signUp").addObject(CommonConst.MEMBER_DTO, memberDto);
	// }

}
