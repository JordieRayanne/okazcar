package com.okazcar.okazcar.models.mongodb;

import com.okazcar.okazcar.models.Voiture;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
        for (Map.Entry<String, MultipartFile> entry : multipartFiles.entrySet()) {
            if (entry.getValue().getResource().getFile().getName().endsWith(".png") || entry.getValue().getResource().getFile().getName().endsWith(".jpeg") || entry.getValue().getResource().getFile().getName().endsWith(".jpg") || entry.getValue().getResource().getFile().getName().endsWith(".gif"))
                imagesBytes.add(Base64.getEncoder().encodeToString(entry.getValue().getBytes()));
            else
                throw new Exception("The file :" + entry.getValue().getResource().getFile().getName() + " is not an image");
        }
        setVoitureId(voiture.getId());
    }
}
