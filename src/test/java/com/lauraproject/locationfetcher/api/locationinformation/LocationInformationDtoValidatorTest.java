package com.lauraproject.locationfetcher.api.locationinformation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lauraproject.locationfetcher.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationInformationDtoValidatorTest {

    @InjectMocks
    private LocationInformationDtoValidator validator;

    @Test
    public void shouldThrowValidationExceptionWhenRequiredFieldsMissing() {
        LocationInformationDto dto = new LocationInformationDto();
        assertThatThrownBy(() -> validator.isValid(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void shouldThrowValidationExceptionWhenDtoIsNull() {
        assertThatThrownBy(() -> validator.isValid(null)).isInstanceOf(ValidationException.class);
    }

}
