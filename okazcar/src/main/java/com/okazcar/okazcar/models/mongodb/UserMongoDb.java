package com.okazcar.okazcar.models.mongodb;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;

import com.okazcar.okazcar.models.dto.*;
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

    public File getImageFile() throws IOException {
        // Decode Base64 string
        byte[] decodedBytes = Base64.getDecoder().decode(image);

        // Convert byte array to image
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));

        // Save image to disk
        File outputfile = new File(userId + ".jpg");
        ImageIO.write(image, "jpg", outputfile);

        return outputfile;
    }

    public UserMongoDb(UserInsertDto userDto, String userId) throws IOException {
        String encodedImage;
        if (userDto.getImageFile() == null)
            encodedImage = Base64.getEncoder().encodeToString(downloadImage(userDto.getImage()));
        else {
            if (userDto.getImageFile().getResource().getFile().getName().endsWith(".png") || userDto.getImageFile().getResource().getFile().getName().endsWith(".jpeg") || userDto.getImageFile().getResource().getFile().getName().endsWith(".jpg") || userDto.getImageFile().getResource().getFile().getName().endsWith(".gif")) {
                encodedImage = Base64.getEncoder().encodeToString(userDto.getImageFile().getBytes());
            } else {
                throw new IOException("The file :" + userDto.getImageFile().getResource().getFile().getName() + " is not an image");
            }
        }
        setUserId(userId);
        setImage(encodedImage);
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
