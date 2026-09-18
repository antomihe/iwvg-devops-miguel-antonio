package es.upm.miw.devops.resources;

import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDto;
import es.upm.miw.devops.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UserResource.USERS)
@RequiredArgsConstructor
public class UserResource {

    public static final String USERS = "/users";
    public static final String ID_ID = "/{id}";

    private final UserService userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User user = userDto.toDomain();
        return new UserDto(this.userService.create(user));
    }

    @GetMapping(ID_ID)
    public UserDto read(@PathVariable UUID id) {
        return new UserDto(this.userService.read(id));
    }

    @GetMapping
    public List<UserDto> readAll() {
        return this.userService.readAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @PutMapping(ID_ID)
    public UserDto update(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        User user = userDto.toDomain();
        return new UserDto(this.userService.update(id, user));
    }

    @DeleteMapping(ID_ID)
    public void delete(@PathVariable UUID id) {
        this.userService.delete(id);
    }
}