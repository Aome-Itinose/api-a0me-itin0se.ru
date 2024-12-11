package org.aome.cvapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link org.aome.cvapi.store.models.ProjectEntity}
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto {
    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    @NotBlank(message = "Name must be not empty")
    String name;

    @JsonProperty("description")
    @NotBlank(message = "Description must be not empty")
    String description;

    @JsonProperty("gitHubLink")
    String gitHubLink;

    @JsonProperty("technologies")
    @NotEmpty(message = "Technologies must be not empty")
    List<String> technologies;

    @JsonProperty("timestamp")
    LocalDateTime timestamp;

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", gitHubLink='" + gitHubLink + '\'' +
                ", technologies=" + technologies +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectDto that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getGitHubLink(), that.getGitHubLink()) && Objects.equals(getTechnologies(), that.getTechnologies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getGitHubLink(), getTechnologies());
    }
}