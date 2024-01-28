package com.okazcar.okazcar.models;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Statistique {
    private Map<String, Long> cardDatas;
    private Map<String, Long> revenuesParMois;
    private Map<String, Long> parametres;
}
