package org.aome.cvapi.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.IdDto;
import org.aome.cvapi.dtos.ProjectDto;
import org.aome.cvapi.services.ProjectService;
import org.aome.cvapi.util.exceptions.ValidationException;
import org.aome.cvapi.util.validation.ExceptionMessageCollector;
import org.aome.cvapi.util.validation.ProjectIdValidator;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/projects/", "/projects"})
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectsService;
    private final ProjectIdValidator projectIdValidator;
    private ExceptionMessageCollector exceptionMessageCollector;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> getProjects() {
        List<ProjectDto> projectsList = projectsService.findAllProjects();
        log.info("Found {} projects", projectsList.size());
        return projectsList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProject(@RequestBody @Validated ProjectDto projectDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(exceptionMessageCollector.collectMessage(bindingResult));
        }
        ObjectId id = projectsService.saveProject(projectDto);
        log.info("Project saved with id {}", id);
    }

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveList(@RequestBody @Validated List<ProjectDto> projectDtoList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(exceptionMessageCollector.collectMessage(bindingResult));
        }
        int savedProjects = projectsService.saveProjectList(projectDtoList);
        log.info("{} projects saved", savedProjects);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@RequestBody @Validated IdDto idDto, BindingResult bindingResult) {
        projectIdValidator.validate(idDto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(exceptionMessageCollector.collectMessage(bindingResult));
        }
        projectsService.deleteProject(idDto.getId());
        log.info("Project deleted with id {}", idDto.getId());
    }
}
