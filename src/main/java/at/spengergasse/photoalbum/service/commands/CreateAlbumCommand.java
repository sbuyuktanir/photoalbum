package at.spengergasse.photoalbum.service.commands;

public record CreateAlbumCommand(String name,
                                 boolean restricted) {
}
