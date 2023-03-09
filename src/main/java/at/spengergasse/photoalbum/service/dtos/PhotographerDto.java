package at.spengergasse.photoalbum.service.dtos;

import at.spengergasse.photoalbum.domain.Photographer;

public record PhotographerDto(String userName, String firstName, String lastName, AddressDto billingAddress) {
    public PhotographerDto(Photographer photographer){
        this(photographer.getUserName(),
                photographer.getFirstName(),
                photographer.getLastName(),
                new AddressDto(photographer.getBillingAddress()));
    }
}
