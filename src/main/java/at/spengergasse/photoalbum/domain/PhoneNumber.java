package at.spengergasse.photoalbum.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder

public class PhoneNumber {

    private Integer countryCode;
    private Integer areaCode;
    private String serialNumber;

}
