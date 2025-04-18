package com.example.guestBook.service;

import com.example.guestBook.domain.Article;
import com.example.guestBook.dto.AddArticleRequest;
import com.example.guestBook.dto.UpdateArticleRequest;
import com.example.guestBook.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;

    // 블로그 글 추가 메소드
    public Article save(AddArticleRequest request) {
        return guestBookRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return guestBookRepository.findAll();
    }

    // 방명록 하나 조회하는 메소드 추가
    public Article findById(Long id) {
        return guestBookRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 방명록의 ID 받은 뒤 db에서 삭제
    public void delete(Long id) {
        guestBookRepository.deleteById(id);
    }

    @DeleteMapping("/api/guestbook/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        guestBookRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    // 방명록 수정하는 메소드
    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = guestBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getAuthor(), request.getContent());

        return article;
    }
}
