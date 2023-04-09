package at.spengergasse.photoalbum.service.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor

public class AddPhotographerForm {

    @NotBlank @Size(min=2, max=64)
    private String userName;

    @NotBlank @Size(min=2, max=64)
    private String firstName;

    @NotBlank @Size(min=2, max=64)
    private String lastName;

    private String billingAddressStreetNumber;

    private String billingAddressZipCode;

    private String billingAddressCity;

    private String billingAddressCountryIso2Code;
}
