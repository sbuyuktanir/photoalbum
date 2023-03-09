package at.spengergasse.photoalbum.service.commands;

public record CreatePhotoCommand(String fileName, String name, Integer width, Integer height, String photographerKey) {
}
