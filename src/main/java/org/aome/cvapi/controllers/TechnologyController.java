package org.aome.cvapi.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.IdDto;
import org.aome.cvapi.dtos.TechnologyDto;
import org.aome.cvapi.services.TechnologyService;
import org.aome.cvapi.util.exceptions.ValidationException;
import org.aome.cvapi.util.validation.ExceptionMessageCollector;
import org.aome.cvapi.util.validation.TechnologyIdValidator;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/technologies", "/technologies/"})
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;
    private final ExceptionMessageCollector exceptionMessageCollector;
    private final TechnologyIdValidator technologyIdValidator;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TechnologyDto> getTechnologyList() {
        List<TechnologyDto> technologies = technologyService.findAllTechnologies();
        log.info("Found {} technologies", technologies.size());
        return technologies;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTechnology(@RequestBody @Validated TechnologyDto technologyDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException(exceptionMessageCollector.collectMessage(bindingResult));
        }
        ObjectId id = technologyService.saveTechnology(technologyDto);
        log.info("Technology saved with id {}", id);
    }

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTechnologyList(@RequestBody @Validated List<TechnologyDto> technologies, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException(exceptionMessageCollector.collectMessage(bindingResult));
        }
        int savedTechnologies = technologyService.saveTechnologyList(technologies);
        log.info("{} technologies saved", savedTechnologies);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteTechnology(@RequestBody @Validated IdDto idDto, BindingResult bindingResult) {
        technologyIdValidator.validate(idDto, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ValidationException(exceptionMessageCollector.collectMessage(bindingResult));
        }
        technologyService.deleteTechnology(idDto.getId());
        log.info("Technology deleted with id {}", idDto.getId());
    }


}

