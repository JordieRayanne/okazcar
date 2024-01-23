package com.okazcar.okazcar.models.dto;

import com.okazcar.okazcar.models.file.AnnonceFile;
import com.okazcar.okazcar.models.file.UserFile;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnonceFileDto {
    private int articleId;
    private String userId;

    public AnnonceFile getArticleFile() {
        Timestamp dateHeureHistorique = Timestamp.valueOf(LocalDateTime.now());
        AnnonceFile annonceFile = new AnnonceFile();
        annonceFile.setArticleId(articleId);
        annonceFile.getUserFiles().add(new UserFile(userId, dateHeureHistorique));
        return annonceFile;
    }
}
