package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        this.assertMobileNotExist(user.getMobile());
        log.info("Creating user with mobile: {}", user.getMobile());
        return this.userRepository.save(user);
    }

    public User read(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found: " + id));
    }

    public List<User> readAll() {
        return this.userRepository.findAll();
    }

    public User update(UUID id, User user) {
        User existingUser = this.read(id);
        if (!existingUser.getMobile().equals(user.getMobile())) {
            this.assertMobileNotExist(user.getMobile());
        }
        existingUser.setMobile(user.getMobile());
        existingUser.setName(user.getName());
        existingUser.setFamilyName(user.getFamilyName());
        existingUser.setActive(user.getActive());
        log.info("Updating user with id: {}", id);
        return this.userRepository.save(existingUser);
    }

    public void delete(UUID id) {
        log.info("Deleting user with id: {}", id);
        this.userRepository.deleteById(id);
    }

    private void assertMobileNotExist(String mobile) {
        if (this.userRepository.existsByMobile(mobile)) {
            throw new ConflictException("Mobile already exists: " + mobile);
        }
    }
}