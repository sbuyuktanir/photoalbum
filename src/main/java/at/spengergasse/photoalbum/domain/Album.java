package at.spengergasse.photoalbum.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public class Album {

    private String name;
    private LocalDateTime creationTS;
    private Boolean restricted;
}
