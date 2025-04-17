package com.example.guestBook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@EnableJpaAuditing // 수정된 날짜가 자동으로 업데이트하도록 설정
class GuestBookApplicationTests {

	@Test
	void contextLoads() {
	}

}
