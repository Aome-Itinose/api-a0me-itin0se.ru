package org.aome.cvapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.ProfileDto;
import org.aome.cvapi.dtos.ProfileSaveDto;
import org.aome.cvapi.store.models.ProfileEntity;
import org.aome.cvapi.store.repositories.ProfileRepository;
import org.aome.cvapi.util.ImageFileManager;
import org.aome.cvapi.util.converters.ProfileConverter;
import org.aome.cvapi.util.exceptions.ImageNotFoundException;
import org.aome.cvapi.util.exceptions.ProfileNotFoundException;
import org.aome.cvapi.util.exceptions.ProfileNotGetException;
import org.aome.cvapi.util.exceptions.ProfileNotSaveException;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public ObjectId saveProfile(ProfileSaveDto profileDto) throws ProfileNotSaveException {
        ProfileEntity savedProfile;
        try {
            savedProfile = profileRepository.save(enrich(ProfileConverter.profileDtoToEntity(profileDto)));
        }catch (IOException e) {
            log.error("Profile not saved: {}", e.getMessage());
            throw new ProfileNotSaveException("Profile not save");
        }
        log.debug("Profile saved");
        return savedProfile.getId();
    }

    public ProfileDto findProfile() throws ProfileNotGetException, ProfileNotFoundException {
        ProfileEntity profile = profileRepository.findTopByOrderByTimestampDesc()
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        ProfileDto profileDto = ProfileConverter.profileEntityToDto(profile);
        log.debug("Profile found: {}", profileDto);
//        throw new ProfileNotFoundException("Profile not found");
        return profileDto;
    }

    public List<ProfileDto> findAllProfiles() {
        List<ProfileEntity> profiles = profileRepository.findAll();
        if(profiles.isEmpty()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        List<ProfileDto> profileDtos = profiles.stream().map(ProfileConverter::profileEntityToDto).toList();
        log.debug("Profiles found: {}", profiles);
        return profileDtos;
    }

    @Transactional
    public ObjectId deleteProfile(String id) {
        ObjectId oId = new ObjectId(id);
        profileRepository.deleteProfileEntityById(oId);
        log.debug("Profile deleted: {}", id);
        return oId;
    }

    public InputStreamResource findProfileImage(String photoPath) throws ImageNotFoundException {
        InputStreamResource inputStreamResource;
        try {
            inputStreamResource = new ImageFileManager().readPhoto(photoPath);
        }catch (IOException e) {
            log.error("Image not found: {}", e.getMessage());
            throw new ImageNotFoundException("Image not found");
        }
        log.debug("Image found: {}", photoPath);
        return inputStreamResource;
    }

    public boolean isProfileExist(ObjectId id) {
        return profileRepository.existsById(id);
    }

    private ProfileEntity enrich(ProfileEntity profile) {
        profile.setTimestamp(LocalDateTime.now());
        return profile;
    }
}
