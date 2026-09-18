package es.upm.miw.devops.infrastructure.data.daos;

import es.upm.miw.devops.infrastructure.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByMobile(String mobile);

    Boolean existsByMobile(String mobile);
}