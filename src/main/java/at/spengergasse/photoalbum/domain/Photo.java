package at.spengergasse.photoalbum.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public class Photo {

    private String fileName;
    private String name;
    private LocalDateTime creationTS;
    private Integer width;
    private Integer height;
    private Orientation orientation;
}
