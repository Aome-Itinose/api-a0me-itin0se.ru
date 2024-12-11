package org.aome.cvapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileSaveDto {
    @JsonProperty("id")
    String id;

    @JsonProperty("fullName")
    @NotBlank(message = "Full name must be not empty")
    String fullName;

    @JsonProperty("aboutMe")
    @NotBlank(message = "Field aboutMe must be not empty")
    String aboutMe;

    @JsonProperty("photo")
    byte[] photo;

    @Override
    public String toString() {
        return "ProfileSaveDto{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProfileSaveDto that)) return false;
        return Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getAboutMe(), that.getAboutMe()) && Objects.deepEquals(getPhoto(), that.getPhoto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getAboutMe(), Arrays.hashCode(getPhoto()));
    }
}
