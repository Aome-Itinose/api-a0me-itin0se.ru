package org.aome.cvapi.util.converters;

import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.TechnologyDto;
import org.aome.cvapi.store.models.TechnologyEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TechnologyConverter {

    public static TechnologyDto technologyEntityToDto(TechnologyEntity entity) {
        TechnologyDto dto = TechnologyDto.builder()
                .id(entity.getId().toString())
                .fullName(entity.getFullName())
                .shortName(entity.getShortName())
                .description(entity.getDescription())
                .build();
        log.debug("{} converted to {}", entity, dto);
        return dto;
    }

    public static TechnologyEntity technologyDtoToEntity(TechnologyDto dto) {
        TechnologyEntity entity = TechnologyEntity.builder()
                .id(dto.getId() == null ? null : new org.bson.types.ObjectId(dto.getId()))
                .fullName(dto.getFullName())
                .shortName(dto.getShortName())
                .description(dto.getDescription())
                .build();
        log.debug("{} converted to {}", dto, entity);
        return entity;
    }

}
