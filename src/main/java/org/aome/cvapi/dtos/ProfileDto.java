package org.aome.cvapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.aome.cvapi.store.models.ProfileEntity;


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
}