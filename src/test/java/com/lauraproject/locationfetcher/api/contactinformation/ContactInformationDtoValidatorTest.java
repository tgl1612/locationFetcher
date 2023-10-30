package com.lauraproject.locationfetcher.api.contactinformation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lauraproject.locationfetcher.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ContactInformationDtoValidatorTest {

    @InjectMocks
    private ContactInformationDtoValidator validator;

    @Test
    public void shouldThrowValidationExceptionWhenRequiredFieldsMissing() {
        ContactInformationDto dto = new ContactInformationDto();
        assertThatThrownBy(() -> validator.isValid(dto)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void shouldThrowValidationExceptionWhenDtoIsNull() {
        assertThatThrownBy(() -> validator.isValid(null)).isInstanceOf(ValidationException.class);
    }
}
