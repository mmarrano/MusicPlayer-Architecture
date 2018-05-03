package com.smartsync.validator;

import com.smartsync.dto.SoundFileDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SoundValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return SoundFileDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        SoundFileDTO soundFileDTO = (SoundFileDTO) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "field.required",
                "User Id must not be empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required",
                "Title must not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "artist", "field.required",
                "Artist must not be empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "filepath", "field.required",
                "Filepath must not be empty");
    }
}