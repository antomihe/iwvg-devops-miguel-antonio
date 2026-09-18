package es.upm.miw.devops.resources.dtos;

import es.upm.miw.devops.infrastructure.data.models.User;
import jakarta.validation.constraints.NotNull;
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

    private UUID id;

    @NotNull
    private String mobile;

    private String name;

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
}