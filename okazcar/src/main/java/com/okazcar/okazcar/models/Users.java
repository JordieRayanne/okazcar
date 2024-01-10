package com.okazcar.okazcar.models;

import com.okazcar.okazcar.models.mongodb.UserMongoDb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Users {
    private Utilisateur utilisateur;
    private UserMongoDb userMongoDb;
}
