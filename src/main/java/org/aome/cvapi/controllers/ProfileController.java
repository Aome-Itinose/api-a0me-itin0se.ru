package org.aome.cvapi.controllers;

import lombok.RequiredArgsConstructor;
import org.aome.cvapi.dtos.ProfileDto;
import org.aome.cvapi.dtos.ProfileSaveDto;
import org.aome.cvapi.services.ProfileService;
import org.aome.cvapi.util.exceptions.ValidationException;
import org.aome.cvapi.util.validation.ExceptionMessageCollector;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final ExceptionMessageCollector collector;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProfile(@RequestBody @Validated ProfileSaveDto profileDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException(collector.collectMessage(bindingResult));
        }
        profileService.saveProfile(profileDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileDto getProfile() {
        return profileService.findProfile();
    }

    @GetMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> getProfileImage(@RequestParam("photoPath") String photoPath){
        MediaType mediaType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(mediaType).body(profileService.findProfileImage(photoPath));
    }
}

