package com.okazcar.okazcar.controllers;

import com.okazcar.okazcar.models.dto.ArticleFileDto;
import com.okazcar.okazcar.models.file.ArticleFile;
import com.okazcar.okazcar.models.file.UserFile;
import com.okazcar.okazcar.services.file.ArticleFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class ArticleFileController {
    private final ArticleFileService articleFileService;

    @Autowired
    public ArticleFileController(ArticleFileService articleFileService) {
       this.articleFileService = articleFileService;
    }

    @PostMapping("/writeFile")
    public String writeArticleFile(@ModelAttribute ArticleFileDto articleFileDto) throws IOException {
        articleFileService.writeFile(articleFileDto.getArticleFile());
        return "Writed";
    }
}
