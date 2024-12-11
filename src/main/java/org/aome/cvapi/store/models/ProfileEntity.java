package org.aome.cvapi.store.models;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;


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

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProfileEntity that)) return false;
        return Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getAboutMe(), that.getAboutMe()) && Objects.equals(getPhotoPath(), that.getPhotoPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getAboutMe(), getPhotoPath());
    }
}

