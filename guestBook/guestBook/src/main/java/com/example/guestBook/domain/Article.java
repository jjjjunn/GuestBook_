package com.example.guestBook.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity // 엔티티 지정
@Table(name = "guestbook")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌터 패턴으로 객체 생성
@EntityListeners(AuditingEntityListener.class) // 최초 입력 시간, 수정 시간 자동 관리 위함
public class Article {

    @Id // id 필드ㅣ 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    private Long id;

    @Column(nullable = false, length=50) // 작성자, Not Null
    private String author;

    @Column(nullable = false, columnDefinition = "TEXT") // 내용, Not Null
    private String content;

    @CreatedDate // 작성 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정 시간
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    public Article(String author, String content) {
        this.author = author;
        this.content = content;
    }

    @PrePersist // 엔티티가 데이터베이스에 저장되기 전에 createdAT을 현재 시간으로 설정
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // 엔티티가 업데이트할 때 updatedAT을 현재 시간으로 설정
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 엔티티에 요청받은 내용으로 값 수정 메소드
    public void update(String author, String content) {
        this.author = author;
        this.content = content;
    }

}
