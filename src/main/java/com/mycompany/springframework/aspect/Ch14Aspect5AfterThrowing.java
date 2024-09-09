package com.mycompany.springframework.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class Ch14Aspect5AfterThrowing {
	
	@AfterThrowing(
			pointcut= "execution(public * com.mycompany.springframework.controller.Ch14Controller.afterThrowing*(..))",
			throwing = "e"
			)
	public void method(Throwable e) {
		//공통 코드(예외 발생 후에 실행하는 코드)
		log.info("예외 발생 후 실행");
		log.info("에외 메세지: " + e.getMessage());
		
		
	}
}
