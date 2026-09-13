package es.upm.miw.devops.services;

import es.upm.miw.devops.data.repositories.UserRepository;
import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.rest.dto.ActiveDto;
import es.upm.miw.devops.rest.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto read(Long id) {
        return this.userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id not found: " + id
                ));
    }

    public Stream<UserDto> search(String name, String email, Boolean billable) {
        return this.userRepository.findAll().stream()
                .filter(user -> name == null || (user.getName() != null && user.getName().contains(name)))
                .filter(user -> email == null || (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)))
                .filter(user -> billable == null || user.isBillable() == billable)
                .map(this::toDto);
    }

    public void delete(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User id not found: " + id
            );
        }
        this.userRepository.deleteById(id);
    }

    public UserDto updateActive(Long id, ActiveDto activeDto) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User id not found: " + id
                ));
        user.setActive(activeDto.getActive());
        return this.toDto(this.userRepository.save(user));
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isBillable(),
                user.getActive()
        );
    }
}