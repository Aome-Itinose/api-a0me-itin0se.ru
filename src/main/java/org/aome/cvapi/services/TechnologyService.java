package org.aome.cvapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aome.cvapi.dtos.TechnologyDto;
import org.aome.cvapi.store.models.TechnologyEntity;
import org.aome.cvapi.store.repositories.TechnologyRepository;
import org.aome.cvapi.util.converters.TechnologyConverter;
import org.aome.cvapi.util.exceptions.TechnologyNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    @Transactional
    public ObjectId saveTechnology(TechnologyDto technologyDto) {
        TechnologyEntity savedEntity = technologyRepository.save(TechnologyConverter.technologyDtoToEntity(technologyDto));
        log.debug("Technology saved");
        return savedEntity.getId();
    }

    @Transactional
    public int saveTechnologyList(List<TechnologyDto> technologyList) {
        int size = technologyRepository.saveAll(technologyList.stream().map(TechnologyConverter::technologyDtoToEntity).toList()).size();
        log.debug("Technologies list saved");
        return size;
    }

    public List<TechnologyDto> findAllTechnologies() throws TechnologyNotFoundException {
        List<TechnologyEntity> technologyEntityList = technologyRepository.findAll();
        if(technologyEntityList.isEmpty()) {
            log.error("No technologies found");
            throw new TechnologyNotFoundException("Technologies not found");
        }
        List<TechnologyDto> technologyDtoList = technologyEntityList.stream().map(TechnologyConverter::technologyEntityToDto).toList();
        log.debug("Found {} technologies", technologyDtoList.size());
//        throw new TechnologyNotFoundException("Technologies not found");
        return technologyDtoList;
    }
}

