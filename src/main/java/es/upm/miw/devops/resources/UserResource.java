package es.upm.miw.devops.resources;

import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserActiveDto;
import es.upm.miw.devops.resources.dtos.UserDto;
import es.upm.miw.devops.services.UserService;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UserResource.USERS)
@RequiredArgsConstructor
public class UserResource {

    public static final String USERS = "/users";
    public static final String ID_ID = "/{id}";
    public static final String ACTIVE = "/active";
    public static final String ID_ACTIVE = "/{id}/active";
    public static final String SEARCH = "/search";

    private final UserService userService;

    @GetMapping
    public List<UserDto> readAll() {
        return this.userService.readAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @GetMapping(ID_ID)
    public UserDto read(@PathVariable UUID id) {
        return new UserDto(this.userService.read(id));
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return new UserDto(this.userService.create(userDto.toUser()));
    }

    // MOCK COMMIT - THIS WOULD BE ADDED IN REGULAR DEVELOP
    @PutMapping(ID_ID)
    public UserDto update(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        return new UserDto(this.userService.update(id, userDto.toUser()));
    }

    @PatchMapping(ID_ACTIVE)
    public UserDto updateActive(@PathVariable UUID id, @Valid @RequestBody UserActiveDto userActiveDto) {
        return new UserDto(this.userService.updateActive(id, userActiveDto.getIsActive()));
    }

    @PatchMapping(ACTIVE)
    public List<UserDto> updateActiveList(@Valid @RequestBody List<@Valid UserActiveDto> userActiveDtoList) {
        List<User> users = userActiveDtoList.stream()
                .map(UserActiveDto::toUser)
                .toList();
        return this.userService.updateActiveList(users).stream()
                .map(UserDto::new)
                .toList();
    }

    @DeleteMapping(ID_ID)
    public void delete(@PathVariable UUID id) {
        this.userService.delete(id);
    }

    @GetMapping(SEARCH)
    public List<UserDto> findByCriteria(@ModelAttribute UserFindCriteria userFindCriteria) {
        return this.userService.findByCriteria(userFindCriteria).stream()
                .map(UserDto::new)
                .toList();
    }
}