package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.ActiveDto;
import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        this.assertMobileNotExist(user.getMobile());
        user.setId(null);
        return this.userRepository.save(user);
    }

    public List<User> readAll() {
        return this.userRepository.findAll();
    }

    public User read(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id not found: " + id));
    }

    public User update(UUID id, User user) {
        User existingUser = this.read(id);
        if (!existingUser.getMobile().equals(user.getMobile())) {
            this.assertMobileNotExist(user.getMobile());
        }
        BeanUtils.copyProperties(user, existingUser, "id");
        return this.userRepository.save(existingUser);
    }

    public User updateActive(UUID id, Boolean active) {
        User user = this.read(id);
        user.setActive(active);
        return this.userRepository.save(user);
    }

    public List<User> updateActiveList(List<ActiveDto> activeDtoList) {
        return activeDtoList.stream()
                .map(activeDto -> this.updateActive(activeDto.getId(), activeDto.getActive()))
                .toList();
    }

    public void delete(UUID id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
        }
    }

    public List<User> findByBillable(Boolean billable) {
        return this.userRepository.findAll().stream()
                .filter(user -> this.isBillable(user) == billable)
                .toList();
    }

    private boolean isBillable(User user) {
        return user.getFirstName() != null && !user.getFirstName().isBlank() &&
                user.getFamilyName() != null && !user.getFamilyName().isBlank() &&
                user.getMobile() != null && !user.getMobile().isBlank();
    }

    private void assertMobileNotExist(String mobile) {
        if (this.userRepository.existsByMobile(mobile)) {
            throw new ConflictException("Mobile already exists: " + mobile);
        }
    }
}