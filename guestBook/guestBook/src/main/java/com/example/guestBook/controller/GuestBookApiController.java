package com.example.guestBook.controller;

import com.example.guestBook.domain.Article;
import com.example.guestBook.dto.AddArticleRequest;
import com.example.guestBook.dto.ArticleResponse;
import com.example.guestBook.dto.UpdateArticleRequest;
import com.example.guestBook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController// HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class GuestBookApiController {

    private final GuestBookService guestBookService;

    // HTTP 메서드가 POST일 때 전달받은 URL 동일하면 메소드로 매핑
    @PostMapping("/api/guestbook")
    // 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = guestBookService.save(request);
        // 요청 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    // 방명록 글 조회 후 반환
    @GetMapping("/api/guestbook")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = guestBookService.findAll().stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    // GET 요청올 때 방명록 조회하기 위해 매핑할 메소드 작성
    @GetMapping("/api/guestbook/{id}")
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id) {
        Article article = guestBookService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // DELETE 요청이 오면 글 삭제 위한 메소드 작성
    @DeleteMapping("/api/guestbook/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        guestBookService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }

    // PUT 요청이 오면 글을 수정하기 위한 UpdateArticle() 메소드
    @PutMapping("/api/guestbook/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = guestBookService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
