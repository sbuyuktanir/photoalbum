package at.spengergasse.photoalbum.persistence.converter;

import at.spengergasse.photoalbum.domain.Orientation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrientationConverterTest {

    private OrientationConverter orientationConverter = new OrientationConverter();

    @Test
    void ensureCorrectHandlingofNullValues(){
        //expect
        assertThat(orientationConverter.convertToDatabaseColumn(null)).isNull();
        assertThat(orientationConverter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void ensureCorrectHandlingofDataQualityProblems(){
        //given
        String dbValue = "X";

        //when
        var iaEx =
                assertThrows(IllegalArgumentException.class,
                        () -> orientationConverter.convertToEntityAttribute("X"));

        //then
        assertThat(iaEx).hasMessageContaining("%s is not a valid".formatted(dbValue));
    }

    @ParameterizedTest
    @MethodSource
    void ensureCorrectHandlingofGivenValues(Orientation javaValue, String dbValue) {
    //expect
        assertThat(orientationConverter.convertToDatabaseColumn(javaValue)).isEqualTo(dbValue);
        assertThat(orientationConverter.convertToEntityAttribute(dbValue)).isEqualTo(javaValue);
    }

    static Stream<Arguments> ensureCorrectHandlingofGivenValues(){
        return Stream.of(
                Arguments.of(Orientation.LANDSCAPE, "L"),
                Arguments.of(Orientation.PORTRAIT, "P"),
                Arguments.of(Orientation.SQUARE, "S")
        );
    }
}