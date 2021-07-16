package pl.marczuk.toolsrent.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marczuk.toolsrent.dto.UserDto;
import pl.marczuk.toolsrent.model.User;
import pl.marczuk.toolsrent.service.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;
    private ModelMapper modelMapper = new ModelMapper();
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        userDTO = modelMapper.map(userService.addUser(user), UserDto.class);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
