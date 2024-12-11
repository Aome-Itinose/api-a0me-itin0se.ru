package org.aome.cvapi.util.converters;

import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.ProjectDto;
import org.aome.cvapi.store.models.ProjectEntity;
import org.bson.types.ObjectId;

@Slf4j
public class ProjectConverter {
    public static ProjectDto projectEntityToDto(ProjectEntity entity) {
        ProjectDto dto = ProjectDto.builder()
                .id(entity.getId() == null ? "" : entity.getId().toString())
                .name(entity.getName())
                .description(entity.getDescription())
                .gitHubLink(entity.getGitHubLink())
                .technologies(entity.getTechnologies())
                .timestamp(entity.getTimestamp())
                .build();
        log.debug("{} converted to {}", entity, dto);
        return dto;
    }

    public static ProjectEntity projectDtoToEntity(ProjectDto dto) {
        ProjectEntity entity = ProjectEntity.builder()
                .id(dto.getId() == null ? new ObjectId() : new ObjectId(dto.getId()))
                .name(dto.getName())
                .description(dto.getDescription())
                .gitHubLink(dto.getGitHubLink())
                .technologies(dto.getTechnologies())
                .timestamp(dto.getTimestamp())
                .build();
        log.debug("{} converted to {}", dto, entity);
        return entity;
    }
}
