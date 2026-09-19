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
public class UserActiveDto {

    private UUID id;

    @NotNull
    private Boolean isActive;

    public UserActiveDto(Boolean isActive) {
        this.isActive = isActive;
    }

    public UserActiveDto(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}