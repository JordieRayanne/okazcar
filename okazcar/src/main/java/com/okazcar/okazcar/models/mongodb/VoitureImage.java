package com.okazcar.okazcar.models.mongodb;

import com.google.common.io.Files;
import com.okazcar.okazcar.handlers.FileUploaderHandler;
import com.okazcar.okazcar.models.Voiture;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "voiture_image")
public class VoitureImage {
    @Id
    @Field("voiture_id")
    private int voitureId;

    @Field("images_bytes")
    private List<String> imagesBytes = new ArrayList<>();

    public VoitureImage(HttpServletRequest request, Voiture voiture) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> multipartFiles = multipartRequest.getMultiFileMap().toSingleValueMap();
        File file;
        for (Map.Entry<String, MultipartFile> entry : multipartFiles.entrySet()) {
            file = FileUploaderHandler.uploadFile(entry.getValue());
            if (file.getName().endsWith(".png") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) {
                this.imagesBytes.add(Base64.getEncoder().encodeToString(Files.toByteArray(file)));
                FileUploaderHandler.deleteFile(file);
            }
            else {
                FileUploaderHandler.deleteFile(file);
                throw new Exception("The file :" + file.getName() + " is not an image");
            }
        }
        setVoitureId(voiture.getId());
    }
}
