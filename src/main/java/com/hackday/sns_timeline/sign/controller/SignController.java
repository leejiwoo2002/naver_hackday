package com.hackday.sns_timeline.sign.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackday.sns_timeline.common.CommonConst;
import com.hackday.sns_timeline.sign.domain.dto.CustomUser;
import com.hackday.sns_timeline.sign.domain.dto.MemberDto;
import com.hackday.sns_timeline.sign.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
@Api(value = "/sign", description = "로그인 관리")
public class SignController {

	final private SignService signService;

	@ApiOperation(
		httpMethod = "GET",
		value = "회원가입 페이지 반환",
		response = ModelAndView.class,
		nickname="getSignUpPage"
	)
	@GetMapping("/up")
	public ModelAndView getSignUpPage(@ModelAttribute MemberDto memberDto) {
		return new ModelAndView(CommonConst.SIGN_UP);
	}

	@ApiOperation(
		httpMethod = "POST",
		value = "회원가입 처리 후 홈 리다이렉트",
		response = String.class,
		nickname="signUp")
	@PostMapping("/up")
	public String signUp(@Valid MemberDto memberDto) throws Exception {
		signService.signUp(memberDto);
		return CommonConst.REDIRECT_INDEX;
	}

	@ApiOperation(
		httpMethod = "GET",
		value = "Spring security 로그인 성공 시 요청 end point, TimeLine 반환",
		response = String.class,
		nickname="signInSuccess"
	)
	@GetMapping("/in")
	public String signInSuccess(@AuthenticationPrincipal CustomUser user) {
		if(user==null) {
			return CommonConst.REDIRECT_INDEX;
		}
		return CommonConst.REDIRECT_TIME_LINE;
	}

	@ApiOperation(
		httpMethod = "GET",
		value = "Spring security 로그인 실패 시 요청 end point, 인덱스 페이지 반환",
		response = String.class,
		nickname="signInFail"
	)
	@GetMapping("/fail")
	public String signInFail(RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute(CommonConst.ERROR, true);
		return CommonConst.REDIRECT_INDEX;
	}
}
