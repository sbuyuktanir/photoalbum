package at.spengergasse.photoalbum.persistence.converter;

import at.spengergasse.photoalbum.domain.Orientation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;
import java.util.function.Function;

@Converter(autoApply = true)
public class OrientationConverter implements AttributeConverter<Orientation, String> {

    private static Function<Orientation, String> toDbValue = (o) -> switch (o) {
        case LANDSCAPE -> "L";
        case SQUARE -> "S";
        case PORTRAIT -> "P";
    };

    private static Function<String, Orientation> fromDbValue = (v) -> switch (v) {
        case "L" -> Orientation.LANDSCAPE;
        case "P" -> Orientation.PORTRAIT;
        case "S" -> Orientation.SQUARE;
        default -> null;
    };

    @Override
    public String convertToDatabaseColumn(Orientation orientation) {
        return Optional.ofNullable(orientation).map(toDbValue).orElse(null);
    }

    @Override
    public Orientation convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue).map(fromDbValue).orElse(null);
    }

}
