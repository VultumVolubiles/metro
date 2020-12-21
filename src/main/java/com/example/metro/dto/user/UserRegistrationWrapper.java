package com.example.metro.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationWrapper {
    private String login;
    private String password;
    private String confirmPassword;

    public boolean validate() {
        return password.equals(confirmPassword);
    }
}
