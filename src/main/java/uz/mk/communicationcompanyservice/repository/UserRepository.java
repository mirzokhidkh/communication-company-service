package uz.mk.communicationcompanyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import uz.mk.communicationcompanyservice.entity.User;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(path = "user",collectionResourceRel = "list")
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, UUID id);

    Optional<User> findByUsername(String username);
}
