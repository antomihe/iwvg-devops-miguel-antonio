package es.upm.miw.devops.resources;

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

    private final UserService userService;

    @GetMapping
    public List<UserDto> readAll() {
        return this.userService.readAll().stream()
                .map(UserDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable UUID id) {
        return new UserDto(this.userService.read(id));
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return new UserDto(this.userService.create(userDto.toUser()));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        return new UserDto(this.userService.update(id, userDto.toUser()));
    }

    @PatchMapping("/{id}/active")
    public UserDto updateActive(@PathVariable UUID id, @RequestBody ActiveDto activeDto) {
        return new UserDto(this.userService.updateActive(id, activeDto.getActive()));
    }

    @PutMapping("/{id}/active")
    public UserDto updateActivePut(@PathVariable UUID id, @RequestBody ActiveDto activeDto) {
        return new UserDto(this.userService.updateActive(id, activeDto.getActive()));
    }

    @PatchMapping("/active")
    public List<UserDto> updateActiveList(@RequestBody List<ActiveDto> activeDtoList) {
        return this.userService.updateActiveList(activeDtoList).stream()
                .map(UserDto::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.userService.delete(id);
    }

    @GetMapping("/search")
    public List<UserDto> findByBillable(@RequestParam Boolean billable) {
        return this.userService.findByBillable(billable).stream()
                .map(UserDto::new)
                .toList();
    }
}