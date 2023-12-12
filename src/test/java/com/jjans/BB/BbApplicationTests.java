package com.jjans.BB;

import com.jjans.BB.Repository.MusicInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = MusicInfoRepository.class))
class BbApplicationTests {

	@Test
	@DisplayName("기본 테스트")
	void contextLoads() {
		System.out.println("load test");
		boolean flag =  true;
        Assertions.assertEquals(flag,false,"flag는 false가 아닙니다.");

	}

}
