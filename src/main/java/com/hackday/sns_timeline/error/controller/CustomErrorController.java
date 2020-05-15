package com.hackday.sns_timeline.error.controller;

import static com.hackday.sns_timeline.common.CommonConst.*;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping(value = "/error")
@Api(value = "/error", description = "에러 관리")
public class CustomErrorController implements ErrorController {

	@ApiOperation(httpMethod = "GET",
		value = "Custom Error 페이지 반환",
		response = String.class,
		nickname="handleError")
	@GetMapping
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

		model.addAttribute("code", status.toString());
		model.addAttribute("msg", httpStatus.getReasonPhrase());

		return "errors/error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
