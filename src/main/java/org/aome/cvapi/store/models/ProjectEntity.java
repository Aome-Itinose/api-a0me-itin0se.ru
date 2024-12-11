package org.aome.cvapi.store.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@Document("projects")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectEntity {

    @Id
    ObjectId id;

    String name;

    String description;

    String gitHubLink;

    List<String> technologies;

    LocalDateTime timestamp;




    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", gitHubLink='" + gitHubLink + '\'' +
                ", technologies=" + technologies +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectEntity that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getGitHubLink(), that.getGitHubLink()) && Objects.equals(getTechnologies(), that.getTechnologies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getGitHubLink(), getTechnologies());
    }
}