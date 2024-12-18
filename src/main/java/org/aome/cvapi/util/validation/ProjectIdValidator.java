package org.aome.cvapi.util.validation;

import lombok.RequiredArgsConstructor;
import org.aome.cvapi.dtos.IdDto;
import org.aome.cvapi.services.ProjectService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProjectIdValidator implements Validator {
    private final ProjectService projectService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(IdDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String projectId = ((IdDto) target).getId();
        if(projectId.isBlank() || !ObjectId.isValid(projectId) || !projectService.isProjectExist(new ObjectId(projectId))) {
            errors.rejectValue("id", "", "Project id is invalid");
        }
    }
}
