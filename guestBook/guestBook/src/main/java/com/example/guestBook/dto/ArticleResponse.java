package com.example.guestBook.dto;

import com.example.guestBook.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {

    private final String author;
    private final String content;

    public ArticleResponse(Article article) {
        this.author = article.getAuthor();
        this.content = article.getContent();
    }
}
