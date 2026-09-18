package es.upm.miw.devops.resources.dtos;

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

    private UUID id;

    @NotBlank
    private String mobile;

    private String firstName;

    private String familyName;

    private Boolean active;

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
        this.firstName = user.getFirstName();
    }

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        user.setFirstName(this.firstName);
        return user;
    }
}