package es.upm.miw.devops.resources;

import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.ActiveDto;
import es.upm.miw.devops.resources.dtos.UserDto;
import es.upm.miw.devops.services.UserService;
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
    public static final String SEARCH = "/search";

    private final UserService userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User user = userDto.toUser();
        User createdUser = this.userService.create(user);
        return new UserDto(createdUser);
    }

    @GetMapping
    public List<UserDto> readAll() {
        return this.userService.readAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @GetMapping(ID_ID)
    public UserDto read(@PathVariable UUID id) {
        User user = this.userService.read(id);
        return new UserDto(user);
    }

    @PutMapping(ID_ID)
    public UserDto update(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        User user = userDto.toUser();
        User updatedUser = this.userService.update(id, user);
        return new UserDto(updatedUser);
    }

    @PutMapping(ID_ID + ACTIVE)
    public UserDto updateActivePut(@PathVariable UUID id, @Valid @RequestBody ActiveDto activeDto) {
        User user = this.userService.updateActive(id, activeDto.getActive());
        return new UserDto(user);
    }

    @PatchMapping(ID_ID + ACTIVE)
    public UserDto updateActive(@PathVariable UUID id, @Valid @RequestBody ActiveDto activeDto) {
        User user = this.userService.updateActive(id, activeDto.getActive());
        return new UserDto(user);
    }

    @PatchMapping
    public List<UserDto> updateActiveList(@RequestBody List<ActiveDto> activeDtoList) {
        return this.userService.updateActiveList(activeDtoList).stream()
                .map(UserDto::new)
                .toList();
    }

    @DeleteMapping(ID_ID)
    public void delete(@PathVariable UUID id) {
        this.userService.delete(id);
    }

    @GetMapping(SEARCH)
    public List<UserDto> findByBillable(@RequestParam Boolean billable) {
        return this.userService.findByBillable(billable).stream()
                .map(UserDto::new)
                .toList();
    }
}