package org.aome.cvapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * DTO for {@link org.aome.cvapi.store.models.TechnologyEntity}
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnologyDto {
    @JsonProperty("id")
    String id;

    @JsonProperty("fullName")
    @NotBlank(message = "Full name must be not blank")
    String fullName;

    @JsonProperty("shortName")
    @NotBlank(message = "Short name must be not blank")
    String shortName;

    @JsonProperty("description")
    @NotBlank(message = "Description must be not blank")
    String description;

    @Override
    public String toString() {
        return "TechnologyDto{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TechnologyDto that)) return false;
        return Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getShortName(), that.getShortName()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getShortName(), getDescription());
    }
}