package com.okazcar.okazcar.models.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDto {
    // By défault, the person1 is the sender
    private String personId1;
    private String username1;
    private String personId2;
    private String username2;
    private String content;
    private MultipartFile file;
}
