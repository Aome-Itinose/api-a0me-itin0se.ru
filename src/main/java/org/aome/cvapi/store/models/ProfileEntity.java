package org.aome.cvapi.store.models;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("profiles")
public class ProfileEntity {
    @Id
    ObjectId id;

    String fullName;

    String aboutMe;

    String photoPath;

    LocalDateTime timestamp;
}

