package com.okazcar.okazcar.services.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.okazcar.okazcar.models.file.AnnonceFile;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class AnnonceFileService {
    File file = new File("historic.json");
    private final List<AnnonceFile> annonceFiles = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        try {
            initArticleFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeFile(AnnonceFile annonceFile) throws IOException {
        initArticleFile();
        boolean resp = file.createNewFile();
        int indexArticleFile = checkArticleFile(annonceFile);
        if (indexArticleFile != -1) {
            this.annonceFiles.get(indexArticleFile).addNewUserFile(annonceFile.getUserFiles());
        } else {
            this.annonceFiles.add(annonceFile);
        }
        objectMapper.writeValue(file, annonceFiles);
    }

    private int checkArticleFile(AnnonceFile newAnnonceFile) {
        int i = 0;
        for (AnnonceFile annonceFile : this.annonceFiles) {
            if (annonceFile.getArticleId() == newAnnonceFile.getArticleId())
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
                AnnonceFile article = objectMapper.convertValue(articleMap, AnnonceFile.class);
                if(checkArticleFile(article) == -1) {
                    this.annonceFiles.add(article);
                }
            }
        } catch (MismatchedInputException e) {
            objectMapper.writeValue(file, "[]");
        }
    }

}
