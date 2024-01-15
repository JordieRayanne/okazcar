package com.okazcar.okazcar.models.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passage {
    private String articleId;
    private List<String> userId;
    private LocalDateTime dateTimePassage;
    private int numberPassage;
}
