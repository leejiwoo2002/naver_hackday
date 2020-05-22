package com.hackday.sns_timeline.error.exceptionHandler;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectException {

	@Pointcut("execution(* com.hackday.sns_timeline.*.service.*serivce.*(..))")
	public void getNullFromRepository() { }
}
