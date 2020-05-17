package com.hackday.sns_timeline.sign.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/")
@Api(value = "/", description = "인덱스 페이지")
public class IndexController {

	@ApiOperation(
		httpMethod = "GET",
		value = "인덱스 페이지 로그인 후에는 TimeLine 반환",
		response = String.class,
		nickname="getIndexPage"
	)
	@GetMapping
	public String getIndexPage(@AuthenticationPrincipal CustomUser user){
		if(user == null) {
			return CommonConst.INDEX;
		}
		else {
			return CommonConst.REDIRECT_TIME_LINE;
		}
	}
}
