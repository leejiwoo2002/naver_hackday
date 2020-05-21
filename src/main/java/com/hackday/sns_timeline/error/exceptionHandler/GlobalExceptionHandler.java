package com.hackday.sns_timeline.error.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hackday.sns_timeline.common.commonEnum.ATTRIBUTE;
import com.hackday.sns_timeline.common.commonEnum.PAGE;
import com.hackday.sns_timeline.error.customException.DuplicationException;
import com.hackday.sns_timeline.error.customException.RepositoryNullException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({RepositoryNullException.class})
	protected ModelAndView repositoryFindNull(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage());

		return new ModelAndView(PAGE.ERROR.getPage()).addObject(ATTRIBUTE.CODE.getName(), HttpStatus.BAD_REQUEST)
			.addObject(ATTRIBUTE.MSG.getName(), ex.getMessage());
	}

	@ExceptionHandler({DuplicationException.class})
	protected ModelAndView emailDuplicate(RuntimeException ex, WebRequest request) {
		log.error(ex.getMessage());

		return new ModelAndView(PAGE.ERROR.getPage()).addObject(ATTRIBUTE.CODE.getName(), HttpStatus.BAD_REQUEST)
			.addObject(ATTRIBUTE.MSG.getName(), ex.getMessage());
	}

}
