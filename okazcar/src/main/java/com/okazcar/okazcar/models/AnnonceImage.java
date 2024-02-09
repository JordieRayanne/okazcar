package com.okazcar.okazcar.models;


import com.okazcar.okazcar.models.mongodb.VoitureImage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AnnonceImage {
    private Annonce annonce;
    private VoitureImage voitureImage;
}
