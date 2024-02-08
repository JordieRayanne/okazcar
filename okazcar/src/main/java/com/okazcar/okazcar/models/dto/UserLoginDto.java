package com.okazcar.okazcar.models.dto;

import com.okazcar.okazcar.exception.ForgetException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginDto {
    private String userId;
    private String email;
    private String password;
    @Setter
    private String fcmToken;
    @Setter
    private String platform = "Email/Phone";
    public void setUserId(String userId) throws ForgetException {
        if (userId == null && email == null)
            throw new ForgetException("Error! You forgot to enter an identifier", new Throwable("You doesn't enter any identifier"));
        this.userId = userId;
    }

    public void setEmail(String email) throws ForgetException {
        if (email == null && userId == null)
            throw new ForgetException("Error! You forgot to enter email", new Throwable("You doesn't enter any email"));
        this.email = email;
    }

    public void setPassword(String password) throws ForgetException {
        if (email == null && password == null)
            throw new ForgetException("Error! You forgot to enter password", new Throwable("You doesn't enter any password"));
        this.password = password;
    }
}
