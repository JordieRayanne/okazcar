package com.okazcar.okazcar.models.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class UserInsertDto {
    private String userId;
    private String email;
    private String username;
    private String password;
    private int role = 1;
    private String platform = "Email/Phone";
    private String phoneNumber;
    private String image;
    private Date birthday;
    private MultipartFile imageFile;
}
