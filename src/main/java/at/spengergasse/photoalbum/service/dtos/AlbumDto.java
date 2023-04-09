package at.spengergasse.photoalbum.service.dtos;

import at.spengergasse.photoalbum.domain.Album;

public record AlbumDto(String mame, boolean restricted) {
    public AlbumDto(Album album){
        this(album.getName(),
                album.getRestricted());
    }
}
