package com.okazcar.okazcar.models;

import com.okazcar.okazcar.models.mongodb.VoitureImage;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VoitureVoitureUtilisateurImage {
    VoitureUtilisateur voitureUtilisateur;
    VoitureImage voitureImage;
}
