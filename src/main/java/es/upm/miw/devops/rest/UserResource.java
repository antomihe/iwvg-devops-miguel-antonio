package es.upm.miw.devops.rest;

import es.upm.miw.devops.rest.dto.ActiveDto;
import es.upm.miw.devops.rest.dto.UserDto;
import es.upm.miw.devops.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/user";
    public static final String SEARCH = "/search";

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable Long id) {
        return this.userService.read(id);
    }

    @GetMapping(SEARCH)
    public Stream<UserDto> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean billable) {

        return this.userService.search(name, email, billable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.userService.delete(id);
    }

    @PutMapping("/{id}/active")
    public UserDto updateActive(@PathVariable Long id, @RequestBody ActiveDto activeDto) {
        return this.userService.updateActive(id, activeDto);
    }
}