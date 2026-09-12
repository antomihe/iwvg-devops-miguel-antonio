package es.upm.miw.devops.rest;

import es.upm.miw.devops.data.daos.UserRepository;
import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.rest.dto.ActiveDto;
import es.upm.miw.devops.rest.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/user";
    public static final String SEARCH = "/search";

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public UserDto read(@PathVariable Long id) {
        return this.userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id not found: " + id
                ));
    }

    @GetMapping(SEARCH)
    public Stream<UserDto> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean billable) {

        return this.userRepository.findAll().stream()
                .filter(user -> name == null || (user.getName() != null && user.getName().contains(name)))
                .filter(user -> email == null || (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)))
                .filter(user -> billable == null || user.isBillable() == billable)
                .map(UserDto::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User id not found: " + id
            );
        }
        this.userRepository.deleteById(id);
    }

    @PutMapping("/{id}/active")
    public UserDto updateActive(@PathVariable Long id, @RequestBody ActiveDto activeDto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id not found: " + id
                ));
        user.setActive(activeDto.getActive());
        return new UserDto(this.userRepository.save(user));
    }
}