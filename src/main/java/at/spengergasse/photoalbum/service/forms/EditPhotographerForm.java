package at.spengergasse.photoalbum.service.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EditPhotographerForm {

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
