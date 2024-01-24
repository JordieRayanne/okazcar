package com.okazcar.okazcar.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private Users moi;
    private List<Users> contacts;
}
