package com.hackday.sns_timeline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.domain.dto.ContentDto;
import com.hackday.sns_timeline.domain.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView contentCreate(@ModelAttribute ContentDto contentDto) {
		return new ModelAndView("contentCreate").addObject(CommonConst.CONTENT_DTO, contentDto);
	}
}
