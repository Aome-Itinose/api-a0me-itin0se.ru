package org.aome.cvapi.store.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Builder
@Document("technologies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnologyEntity {

    @Id
    ObjectId id;

    String fullName;

    String shortName;

    String description;


    @Override
    public String toString() {
        return "TechnologyEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TechnologyEntity that)) return false;
        return Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getShortName(), that.getShortName()) && Objects.equals(getDescription(), that.getDescription());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getShortName(), getDescription());
    }
}