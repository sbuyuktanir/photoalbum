package at.spengergasse.photoalbum.persistence.converter;

import at.spengergasse.photoalbum.domain.EmailType;
import at.spengergasse.photoalbum.domain.Orientation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailAddressTypeConverterTest {

    private EmailTypeConverter emailTypeConverter = new EmailTypeConverter();

    @Test
    void ensureCorrectHandlingofNullValues(){
        //expect
        assertThat(emailTypeConverter.convertToDatabaseColumn(null)).isNull();
        assertThat(emailTypeConverter.convertToEntityAttribute(null)).isNull();
    }

    @Disabled
    @Test
    void ensureCorrectHandlingofDataQualityProblems(){
        //given
        String dbValue = "X";

        //when
        var iaEx =
                assertThrows(IllegalArgumentException.class,
                        () -> emailTypeConverter.convertToEntityAttribute("X"));

        //then
        assertThat(iaEx).hasMessageContaining("%s is not a valid".formatted(dbValue));
    }

    @ParameterizedTest
    @MethodSource
    void ensureCorrectHandlingofGivenValues(EmailType javaValue, String dbValue) {
        //expect
        assertThat(emailTypeConverter.convertToDatabaseColumn(javaValue)).isEqualTo(dbValue);
        assertThat(emailTypeConverter.convertToEntityAttribute(dbValue)).isEqualTo(javaValue);
    }

    static Stream<Arguments> ensureCorrectHandlingofGivenValues(){
        return Stream.of(
                Arguments.of(EmailType.BUSINESS, "B"),
                Arguments.of(EmailType.PRIVATE, "P")
        );
    }
}