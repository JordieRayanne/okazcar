package com.okazcar.okazcar.models;

import com.okazcar.okazcar.models.mongodb.VoitureImage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientVoitureImage {
    private ClientVoiture clientVoiture;
    private VoitureImage voitureImage;
}
