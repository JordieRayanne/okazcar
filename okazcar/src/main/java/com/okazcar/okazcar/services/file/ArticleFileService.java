package com.okazcar.okazcar.services.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.okazcar.okazcar.models.file.ArticleFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleFileService {
    File file = new File("historic.json");
    private final List<ArticleFile> articleFiles = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        try {
            initArticleFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeFile(ArticleFile articleFile) throws IOException {
        initArticleFile();
        boolean resp = file.createNewFile();
        int indexArticleFile = checkArticleFile(articleFile);
        if (indexArticleFile != -1) {
            this.articleFiles.get(indexArticleFile).addNewUserFile(articleFile.getUserFiles());
        } else {
            this.articleFiles.add(articleFile);
        }
        objectMapper.writeValue(file, articleFiles);
    }

    private int checkArticleFile(ArticleFile newArticleFile) {
        int i = 0;
        for (ArticleFile articleFile: this.articleFiles) {
            if (articleFile.getArticleId() == newArticleFile.getArticleId())
                return i;
            i++;
        }
        return -1;
    }

    private void initArticleFile() throws IOException {
        List<Map<String, Object>> articlesList;
        try {
            articlesList = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> articleMap : articlesList) {
                ArticleFile article = objectMapper.convertValue(articleMap, ArticleFile.class);
                if(checkArticleFile(article) == -1) {
                    this.articleFiles.add(article);
                }
            }
        } catch (MismatchedInputException e) {
            objectMapper.writeValue(file, "[]");
        }
    }

}
