package com.okazcar.okazcar.models.mongodb;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import com.okazcar.okazcar.models.dto.*;

import static com.okazcar.okazcar.handlers.FileUploaderHandler.deleteFile;
import static com.okazcar.okazcar.handlers.FileUploaderHandler.uploadFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "user")
public class UserMongoDb {
    @Id
    @Field("user_id")
    private String userId;

    @Field("image")
    private String image;


    public UserMongoDb(UserInsertDto userDto, String userId) throws IOException {
        String encodedImage;
        File file = uploadFile(userDto.getImageFile());
        if (userDto.getImageFile() == null)
            encodedImage = Base64.getEncoder().encodeToString(downloadImage(userDto.getImage()));
        else {
            if (file.getName().endsWith(".png") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) {
                encodedImage = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
            } else {
                throw new IOException("The file : " + file.getName() + " is not an image");
            }
        }
        setUserId(userId);
        setImage(encodedImage);
        deleteFile(file);
    }

    private byte[] downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        return out.toByteArray();
    }
}
