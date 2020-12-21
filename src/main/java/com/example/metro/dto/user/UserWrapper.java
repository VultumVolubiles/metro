package com.example.metro.dto.user;

import com.example.metro.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWrapper {
    private Long id;
    private String login;
    private String password;

    public UserWrapper() {}

    public UserWrapper(User user) {
        id = user.getId();
        login = user.getLogin();
    }
}
