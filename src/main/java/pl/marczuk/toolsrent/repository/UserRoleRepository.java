package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marczuk.toolsrent.model.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByType(String role);
}
