package org.aome.cvapi.services;

import lombok.RequiredArgsConstructor;
import org.aome.cvapi.dtos.ProfileDto;
import org.aome.cvapi.dtos.ProfileSaveDto;
import org.aome.cvapi.store.models.ProfileEntity;
import org.aome.cvapi.store.repositories.ProfileRepository;
import org.aome.cvapi.util.ImageFileManager;
import org.aome.cvapi.util.ProfileConverter;
import org.aome.cvapi.util.exceptions.ImageNotFoundException;
import org.aome.cvapi.util.exceptions.ProfileNotFoundException;
import org.aome.cvapi.util.exceptions.ProfileNotGetException;
import org.aome.cvapi.util.exceptions.ProfileNotSaveException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public void saveProfile(ProfileSaveDto profileDto) throws ProfileNotSaveException {
        try {
            profileRepository.save(enrich(ProfileConverter.profileDtoToEntity(profileDto)));
        }catch (IOException e) {
            //Todo: add logger
            throw new ProfileNotSaveException("Profile not save");
        }
    }

    public ProfileDto findProfile() throws ProfileNotGetException, ProfileNotFoundException {
        ProfileEntity profile = profileRepository.findTopByOrderByTimestampDesc()
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
        ProfileDto profileDto;
        try{
            profileDto = ProfileConverter.profileEntityToDto(profile);
        }catch (IOException e) {
            //Todo: add logger
            throw new ProfileNotGetException("Profile not get");
        }
        return profileDto;
    }

    public InputStreamResource findProfileImage(String photoPath) throws ImageNotFoundException {
        InputStreamResource inputStreamResource;
        try {
            inputStreamResource = new ImageFileManager().readPhoto(photoPath);
        }catch (IOException e) {
            //Todo: add logger
            throw new ImageNotFoundException("Image not found");
        }
        return inputStreamResource;
    }

    private ProfileEntity enrich(ProfileEntity profile) {
        profile.setTimestamp(LocalDateTime.now());
        return profile;
    }
}
