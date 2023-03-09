package at.spengergasse.photoalbum.service.commands;

public record CreatePhotographerCommand(String userName,
                                        String firstName,
                                        String lastName,
                                        String billingAddressStreetNumber,
                                        String billingAddressZipCode,
                                        String billingAddressCity,
                                        String billingAddressCountryIso2Code) {
}
