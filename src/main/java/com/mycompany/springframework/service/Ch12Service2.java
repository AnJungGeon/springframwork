package com.mycompany.springframework.service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Ch12Service2 {
	//정적메소드
	public static Ch12Service2 getInstance() {
		log.info("실행");
		return new Ch12Service2();
	}
	
	//인스턴스 메소드 - 객체를 생성 후 호출해야 한다.
	public Ch12Service3 getCh12Service3() {
		return new Ch12Service3();
	}
}
