package com.okazcar.okazcar.models.dto;

import com.okazcar.okazcar.models.file.ArticleFile;
import com.okazcar.okazcar.models.file.UserFile;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleFileDto {
    private int articleId;
    private String userId;
    private Timestamp dateHeureHistorique = Timestamp.valueOf(LocalDateTime.now());

    public ArticleFile getArticleFile() {
        ArticleFile articleFile = new ArticleFile();
        articleFile.setArticleId(articleId);
        articleFile.getUserFiles().add(new UserFile(userId, dateHeureHistorique));
        return articleFile;
    }
}
