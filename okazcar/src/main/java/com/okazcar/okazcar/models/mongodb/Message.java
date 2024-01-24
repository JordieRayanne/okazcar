package com.okazcar.okazcar.models.mongodb;

import com.google.common.io.Files;
import com.okazcar.okazcar.handlers.FileUploaderHandler;
import com.okazcar.okazcar.models.dto.MessageDto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Field("content")
    private String content;

    @Field("senderId")
    private String senderId;

    @Field("dateTimeSend")
    private LocalDateTime dateTimeSend = LocalDateTime.now();

    @Field("type")
    private String type = "MESSAGE";

    public Message(MessageDto messageDto) throws IOException {
        if (messageDto.getFile() == null && messageDto.getContent() != null) {
            this.content = messageDto.getContent();
            this.setType("MESSAGE");
        } else {
            assert messageDto.getFile() != null;
            File file = FileUploaderHandler.uploadFile(messageDto.getFile());
            this.content = Base64.getEncoder().encodeToString(Files.toByteArray(file));
            FileUploaderHandler.deleteFile(file);
            this.setType("FILE");
        }
        this.senderId = messageDto.getPersonId1();
    }
}

