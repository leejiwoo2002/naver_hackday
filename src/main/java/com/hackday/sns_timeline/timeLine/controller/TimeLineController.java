package com.hackday.sns_timeline.timeLine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/timeLine")
public class TimeLineController {

	@GetMapping
	public ModelAndView getTimeLinePage(){
		return new ModelAndView("timeLine");
	}
}
