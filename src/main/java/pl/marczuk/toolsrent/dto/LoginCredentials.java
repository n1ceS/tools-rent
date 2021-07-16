package pl.marczuk.toolsrent.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginCredentials {

    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}