package pl.marczuk.toolsrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marczuk.toolsrent.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);
}
