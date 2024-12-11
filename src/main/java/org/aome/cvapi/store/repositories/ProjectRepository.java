package org.aome.cvapi.store.repositories;

import org.aome.cvapi.store.models.ProjectEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<ProjectEntity, ObjectId> {
}