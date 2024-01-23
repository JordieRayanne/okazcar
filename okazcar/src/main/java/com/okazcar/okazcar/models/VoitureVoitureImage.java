package com.okazcar.okazcar.models;

import com.okazcar.okazcar.models.mongodb.VoitureImage;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VoitureVoitureImage {
    private VoitureImage voitureImage;
    private Voiture voiture;
}
