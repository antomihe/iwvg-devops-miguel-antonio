package es.upm.miw.devops.resources.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.upm.miw.devops.infrastructure.data.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String mobile;

    @NotBlank
    private String firstName;

    private String familyName;

    private Boolean active;

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public User toDomain() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

    public User toUser() {
        return this.toDomain();
    }
}