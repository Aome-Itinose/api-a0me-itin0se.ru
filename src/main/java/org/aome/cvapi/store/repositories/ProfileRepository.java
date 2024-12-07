package org.aome.cvapi.store.repositories;

import org.aome.cvapi.store.models.ProfileEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, ObjectId> {
    Optional<ProfileEntity> findTopByOrderByTimestampDesc();
}