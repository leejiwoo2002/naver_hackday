package com.hackday.sns_timeline.sign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// 컨트롤러 빈 등록
@Controller
// Log4j2 로그를 사용하기 위한 어노테으션
@Log4j2
// 자동으로 생성자 주입방식으로 빈을 가져옴
@RequiredArgsConstructor
public class IndexController {

	// 인덱스 페이지
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndexPage(){
		return new ModelAndView("index");
	}

}
