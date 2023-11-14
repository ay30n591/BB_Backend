package com.jjans.BB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BbApplicationTests {

	@Test
	@DisplayName("기본 테스트")
	void contextLoads() {
		System.out.println("load test");
		boolean flag =  true;
        Assertions.assertEquals(flag,false,"flag는 false가 아닙니다.");

	}

}
