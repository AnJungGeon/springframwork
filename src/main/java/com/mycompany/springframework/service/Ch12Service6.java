package com.mycompany.springframework.service;

import com.mycompany.springframework.dao.Ch12Dao1;
import com.mycompany.springframework.dao.Ch12Dao2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

//어노테이션 말고 xml 방식으로 
@Slf4j
@Data
//@AllArgsConstructor
public class Ch12Service6 {
	private Ch12Dao1 dao1;
	private Ch12Dao2 dao2;
	
	public Ch12Service6(Ch12Dao1 dao1, Ch12Dao2 dao2) {
		this.dao1 = dao1;
		this.dao2 = dao2;
		log.info("실행"); //@AllArgsConstructor만 붙이면 되지만 로그 확인을 위해 생성
	}
	
	
}
