package com.hackday.sns_timeline.controller;

import javax.validation.Valid;

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
	public ModelAndView getCreatePage(@ModelAttribute ContentDto contentDto) {
		return new ModelAndView("contentCreate").addObject(CommonConst.CONTENT_DTO, contentDto);
	}

/*	@RequestMapping(value = "/create/do", method = RequestMethod.POST)
	public ModelAndView contentCreate(@ModelAttribute(CommonConst.CONTENT_DTO) @Valid ContentDto contentDto) throws Exception {
		log.info(contentDto.gettitle() + " tries to create content");

		signService.signUp(contentDto);

		log.info(memberDto.getEmail() + " succeeds to log up");
		return new ModelAndView("index").addObject(CommonConst.MEMBER_DTO, new MemberDto());
	}*/
}
