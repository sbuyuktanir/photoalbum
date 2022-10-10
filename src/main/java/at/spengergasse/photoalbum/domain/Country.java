package at.spengergasse.photoalbum.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public class Country {

    private String name;
    private String iso2Code;

}
