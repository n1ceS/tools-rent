package pl.marczuk.toolsrent.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.marczuk.toolsrent.dto.LoginCredentials;

import javax.validation.Valid;

@RestController
public class LoginController {

    //added only for swagger functionality endpoint
    @PostMapping("/login")
    public void login(@RequestBody @Valid LoginCredentials credentials) {

    }
}