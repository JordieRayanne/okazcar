package com.okazcar.okazcar.models;

import com.okazcar.okazcar.models.mongodb.VoitureImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VAnnonceImage {
    private V_Annonce vAnnonce;
    private VoitureImage voitureImage;
}
