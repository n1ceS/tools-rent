package pl.marczuk.toolsrent.service;

import pl.marczuk.toolsrent.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);

    User addUser(User user);
}
