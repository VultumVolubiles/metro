package com.example.metro.controller.view;

import com.example.metro.dto.user.UserRegistrationWrapper;
import com.example.metro.dto.user.UserWrapper;
import com.example.metro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("registration")
    public String registrationView(WebRequest request, Model model) {
        UserRegistrationWrapper wrapper = new UserRegistrationWrapper();
        model.addAttribute("user", wrapper);
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registration(@ModelAttribute("user") UserRegistrationWrapper wrapper,
                                     HttpServletRequest request) {
        UserWrapper user = service.registration(wrapper);
        return new ModelAndView("login", "user", user);
    }
}
