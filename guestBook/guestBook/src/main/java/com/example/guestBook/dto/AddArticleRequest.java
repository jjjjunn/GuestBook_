package com.example.guestBook.dto;

import com.example.guestBook.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자
@Getter
public class AddArticleRequest {

    private String author;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .author(author)
                .content(content)
                .build();
    }
}
