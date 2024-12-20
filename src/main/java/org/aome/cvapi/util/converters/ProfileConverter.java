package org.aome.cvapi.util.converters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.ProfileDto;
import org.aome.cvapi.dtos.ProfileSaveDto;
import org.aome.cvapi.store.models.ProfileEntity;
import org.aome.cvapi.util.ImageFileManager;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileConverter {


    public static ProfileEntity profileDtoToEntity(ProfileSaveDto dto) throws IOException {
        ProfileEntity entity = ProfileEntity
                .builder()
                .id(dto.getId() == null ? new ObjectId() : new ObjectId(dto.getId()))
                .fullName(dto.getFullName())
                .aboutMe(dto.getAboutMe())
                .photoPath(new ImageFileManager().writePhoto(dto.getPhoto(), dto.getFullName()))
                .build();
        log.debug("{} converted to {}", dto, entity);
        return entity;
    }

    public static ProfileDto profileEntityToDto(ProfileEntity entity){
        ProfileDto dto = ProfileDto
                .builder()
                .id(entity.getId() == null ? "" : entity.getId().toString())
                .fullName(entity.getFullName())
                .aboutMe(entity.getAboutMe())
                .photoPath(entity.getPhotoPath())
                .build();
        log.debug("{} converted to {}", entity, dto);
        return dto;
    }

}
