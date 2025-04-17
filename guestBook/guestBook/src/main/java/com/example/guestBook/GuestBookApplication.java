package com.example.guestBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 수정된 날짜가 자동으로 업데이트하도록 설정
public class GuestBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestBookApplication.class, args);
	}

}
