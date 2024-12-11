package org.aome.cvapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.aome.cvapi.store.models.ProfileEntity;

import java.util.Objects;


/**
 * DTO for {@link ProfileEntity}
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDto {

    @JsonProperty("id")
    String id;

    @JsonProperty("fullName")
    @NotBlank(message = "Full name must be not empty")
    String fullName;

    @JsonProperty("aboutMe")
    @NotBlank(message = "Field aboutMe must be not empty")
    String aboutMe;

    @JsonProperty("photoPath")
    String photoPath;

    @Override
    public String toString() {
        return "ProfileDto{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProfileDto that)) return false;
        return Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getAboutMe(), that.getAboutMe()) && Objects.equals(getPhotoPath(), that.getPhotoPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getAboutMe(), getPhotoPath());
    }
}