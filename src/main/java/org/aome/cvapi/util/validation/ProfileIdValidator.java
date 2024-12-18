package org.aome.cvapi.util.validation;

import lombok.RequiredArgsConstructor;
import org.aome.cvapi.dtos.IdDto;
import org.aome.cvapi.services.ProfileService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProfileIdValidator implements Validator {
    private final ProfileService profileService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(IdDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String profileId = ((IdDto) target).getId();

        if (profileId.isBlank() || !ObjectId.isValid(profileId) || !profileService.isProfileExist(new ObjectId(profileId))) {
            errors.rejectValue("id", "", "Profile id is invalid");

        }
    }
}
