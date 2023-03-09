package at.spengergasse.photoalbum.persistence.projections;

import java.time.LocalDateTime;

public class Projections {

    public static record AlbumInfoDTO(String name) {}

    public static record PhotoInfoDTO(String name, LocalDateTime creationTS) {}

}
