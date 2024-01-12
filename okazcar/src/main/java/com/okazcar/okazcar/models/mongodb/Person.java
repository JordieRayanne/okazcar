package com.okazcar.okazcar.models.mongodb;

import com.okazcar.okazcar.models.Utilisateur;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Field("personId")
    private String personId;

    @Field("personName")
    private String personName;

    public Person(Utilisateur utilisateur) {
        this.personId = utilisateur.getUtilisateurId();
        this.personName = utilisateur.getUsername();
    }

}
