package at.spengergasse.photoalbum.persistence.converter;

import at.spengergasse.photoalbum.domain.Orientation;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrientationConverter extends AbstractEnumToStringConverter<Orientation> {

    public OrientationConverter() {

        super((o) -> switch (o) {
            case LANDSCAPE -> "L";
            case SQUARE -> "S";
            case PORTRAIT -> "P";
        }, (v) -> switch (v) {
            case "L" -> Orientation.LANDSCAPE;
            case "P" -> Orientation.PORTRAIT;
            case "S" -> Orientation.SQUARE;
        //    default -> null;
            default -> throw new IllegalArgumentException("Data Quality Problem in DB: %s is not a vlaid orientation value!".formatted(v));
        });

    }

}
