package org.aome.cvapi.store.repositories;

import org.aome.cvapi.store.models.TechnologyEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TechnologyRepository extends MongoRepository<TechnologyEntity, ObjectId> {
}