package org.aome.cvapi.util;

import lombok.RequiredArgsConstructor;
import org.aome.cvapi.dtos.ProfileDto;
import org.aome.cvapi.dtos.ProfileSaveDto;
import org.aome.cvapi.store.models.ProfileEntity;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ProfileConverter {

    public static ProfileEntity profileDtoToEntity(ProfileSaveDto dto) throws IOException {
        return ProfileEntity
                .builder()
                .id(dto.getId() == null ? new ObjectId() : new ObjectId(dto.getId()))
                .fullName(dto.getFullName())
                .aboutMe(dto.getAboutMe())
                .photoPath(new ImageFileManager().writePhoto(dto.getPhoto(), dto.getFullName()))
                .build();
    }

    public static ProfileDto profileEntityToDto(ProfileEntity entity) throws IOException {
        return ProfileDto
                .builder()
                .id(entity.getId() == null ? "" : entity.getId().toString())
                .fullName(entity.getFullName())
                .aboutMe(entity.getAboutMe())
                .photoPath(entity.getPhotoPath())
                .build();
    }

}
