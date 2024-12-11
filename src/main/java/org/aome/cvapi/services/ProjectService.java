package org.aome.cvapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.ProjectDto;
import org.aome.cvapi.store.models.ProjectEntity;
import org.aome.cvapi.store.repositories.ProjectRepository;
import org.aome.cvapi.util.converters.ProjectConverter;
import org.aome.cvapi.util.exceptions.ProjectNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public ObjectId saveProject(ProjectDto projectDto) {
        ProjectEntity savedProject = projectRepository.save(enrich(ProjectConverter.projectDtoToEntity(projectDto)));
        log.debug("Project saved");
        return savedProject.getId();
    }

    @Transactional
    public int saveProjectList(List<ProjectDto> projectDtoList) {
        int size = projectRepository.saveAll(projectDtoList.stream().map(ProjectConverter::projectDtoToEntity).toList()).size();
        log.debug("Projects list saved");
        return size;
    }

    public List<ProjectDto> findAllProjects() throws ProjectNotFoundException{
        List<ProjectEntity> projectsList = projectRepository.findAll();
        if(projectsList.isEmpty()){
            log.error("No projects found");
            throw new ProjectNotFoundException("Projects not found");
        }
        log.debug("Found {} projects", projectsList.size());
//        throw new ProjectNotFoundException("Projects not found");
        return projectsList.stream().map(ProjectConverter::projectEntityToDto).toList();
    }

    private ProjectEntity enrich(ProjectEntity entity) {
        if(entity.getTimestamp() == null) {
            entity.setTimestamp(LocalDateTime.now());
        }
        return entity;
    }
}
