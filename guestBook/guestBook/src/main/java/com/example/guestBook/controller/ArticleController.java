package com.example.guestBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @GetMapping("/guestbook")
    public String newArticleForm() {
        return "index";
    }

    @PostMapping("/guestbook/create")
    public String createArticle() {
        return "redirect:/guestbook";
    }


}
