package org.aome.cvapi.util.validation;

import lombok.RequiredArgsConstructor;
import org.aome.cvapi.dtos.IdDto;
import org.aome.cvapi.services.TechnologyService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class TechnologyIdValidator implements Validator {
    private final TechnologyService technologyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(IdDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String technologyId = ((IdDto) target).getId();
        if(technologyId.isBlank() || !ObjectId.isValid(technologyId) || !technologyService.isTechnologyExist(new ObjectId(technologyId))) {
            errors.rejectValue("id", "", "Technology id is invalid");
        }
    }
}
