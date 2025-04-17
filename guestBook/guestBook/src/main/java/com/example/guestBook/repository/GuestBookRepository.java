package com.example.guestBook.repository;

import com.example.guestBook.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<Article, Long> {
}
