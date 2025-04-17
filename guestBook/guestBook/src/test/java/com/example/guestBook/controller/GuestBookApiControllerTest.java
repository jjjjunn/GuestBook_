package com.example.guestBook.controller;

import com.example.guestBook.domain.Article;
import com.example.guestBook.dto.AddArticleRequest;
import com.example.guestBook.dto.UpdateArticleRequest;
import com.example.guestBook.repository.GuestBookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성
class GuestBookApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    GuestBookRepository guestBookRepository;

    @BeforeEach // 테스트 실행 전 실행 메소드
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        guestBookRepository.deleteAll();
    }

    @DisplayName("addArticle: 방명록 추가 성공")
    @Test
    public void addArticle() throws Exception {
        //given
        final String url = "/api/guestbook";
        final String author = "author";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(author, content);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = guestBookRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(articles.get(0).getAuthor()).isEqualTo(author);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles: 방명록 글 목록 조회 성공")
    @Test
    public void findAllArticles() throws Exception {
        //given
        final String url = "/api/guestbook";
        final String author = "author";
        final String content = "content";

        guestBookRepository.save(Article.builder()
                .author(author)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].author").value(author));
    }

    @DisplayName("findArticle: 방명록 글 조회 성공")
    @Test
    public void findArticle() throws Exception {
        //given
        final String url = "/api/guestbook/{id}";
        final String author = "author";
        final String content = "content";

        Article savedArticle = guestBookRepository.save(Article.builder()
                .author(author)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.author").value(author));
    }

    @DisplayName("deleteArticle: 방명록 글 삭제 성공")
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/guestbook/{id}";
        final String author = "author";
        final String content = "content";

        Article savedArticle = guestBookRepository.save(Article.builder()
                .author(author)
                .content(content)
                .build());

        //when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        //then
        List<Article> articles = guestBookRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정 성공")
    @Test
    public void updateArticle() throws Exception {
        //given
        final String url = "/api/guestbook/{id}";
        final String author = "author";
        final String content = "content";

        Article savedArticle = guestBookRepository.save(Article.builder()
                .author(author)
                .content(content)
                .build());

        final String newAuthor = "new Author";
        final String newContent = "new Content";

        UpdateArticleRequest request = new UpdateArticleRequest(newAuthor, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //then
        result.andExpect(status().isOk());

        Article article = guestBookRepository.findById(savedArticle.getId()).get();

        assertThat(article.getAuthor()).isEqualTo(newAuthor);
        assertThat(article.getContent()).isEqualTo(newContent);
    }

}