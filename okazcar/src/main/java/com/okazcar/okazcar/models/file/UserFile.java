package com.okazcar.okazcar.models.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFile {
    private String userId;
    private Timestamp dateHeureHistorique;
}
