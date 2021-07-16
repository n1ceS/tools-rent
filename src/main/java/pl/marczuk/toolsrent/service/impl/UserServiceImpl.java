package pl.marczuk.toolsrent.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marczuk.toolsrent.exception.BadRequestException;
import pl.marczuk.toolsrent.exception.ResourceNotFoundException;
import pl.marczuk.toolsrent.model.User;
import pl.marczuk.toolsrent.model.UserRole;
import pl.marczuk.toolsrent.payload.ApiResponse;
import pl.marczuk.toolsrent.repository.UserRepository;
import pl.marczuk.toolsrent.repository.UserRoleRepository;
import pl.marczuk.toolsrent.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional
    public User addUser(User user) {
        if(userRepository.existsById(user.getUsername())) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Username is already taken!");
            throw new BadRequestException(apiResponse);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        addRoleToUser(user.getUsername(), "USER");
        return user;
    }

    @Transactional
    public User addRoleToUser(String username, String role) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user", "id", username));
        UserRole userRole = userRoleRepository.findByType(role).orElseThrow(() -> new ResourceNotFoundException("role", "type", role));
        user.getUserRoleSet().add(userRole);
        return user;
    }
}
