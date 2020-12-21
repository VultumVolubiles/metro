package com.example.metro.service;

import com.example.metro.dto.user.UserDetail;
import com.example.metro.dto.user.UserRegistrationWrapper;
import com.example.metro.dto.user.UserWrapper;
import com.example.metro.entity.User;
import com.example.metro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserWrapper registration(UserRegistrationWrapper wrapper) {
        User user = repository.findByLogin(wrapper.getLogin());
        if (user != null)
            throw new IllegalArgumentException("User with login {" + wrapper.getLogin() + "} already exist");

        if (!wrapper.validate())
            throw new IllegalArgumentException("Password and Confirm password not equals");

        user = new User();
        user.setLogin(wrapper.getLogin());
        user.setPassword(encoder.encode(wrapper.getPassword()));
        return new UserWrapper(repository.save(user));
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findByLogin(s);
        if (user == null)
            throw new UsernameNotFoundException("User with login {" + s + "} not found");

        return new UserDetail(user);
    }

    public PasswordEncoder getEncoder() {
        return encoder;
    }
}
