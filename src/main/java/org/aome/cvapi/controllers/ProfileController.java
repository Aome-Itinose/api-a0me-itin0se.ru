package org.aome.cvapi.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.IdDto;
import org.aome.cvapi.dtos.ProfileDto;
import org.aome.cvapi.dtos.ProfileSaveDto;
import org.aome.cvapi.services.ProfileService;
import org.aome.cvapi.util.exceptions.ValidationException;
import org.aome.cvapi.util.validation.ExceptionMessageCollector;
import org.aome.cvapi.util.validation.ProfileIdValidator;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final ExceptionMessageCollector collector;
    private final ProfileIdValidator profileIdValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProfile(@RequestBody @Validated ProfileSaveDto profileDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException(collector.collectMessage(bindingResult));
        }
        ObjectId id = profileService.saveProfile(profileDto);
        log.info("Profile saved with id: {}", id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileDto getProfile() {
        ProfileDto profile = profileService.findProfile();
        log.info("Profile found with id: {}", profile.getId());
        return profile;
    }

    @GetMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> getProfileImage(@RequestParam("photoPath") String photoPath){
        MediaType mediaType = MediaType.IMAGE_JPEG;
        log.info("Profile image found with path: {}", photoPath);
        return ResponseEntity.ok().contentType(mediaType).body(profileService.findProfileImage(photoPath));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ProfileDto> getProfileList() {
        List<ProfileDto> profiles = profileService.findAllProfiles();
        log.info("Profile list found with size: {}", profiles.size());
        return profiles;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteProfile(@RequestBody @Validated IdDto idDto, BindingResult bindingResult) {
        profileIdValidator.validate(idDto, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ValidationException(collector.collectMessage(bindingResult));
        }
        profileService.deleteProfile(idDto.getId());
        log.info("Profile deleted with id: {}", idDto.getId());
    }
}

