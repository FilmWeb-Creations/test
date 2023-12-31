package com.example.MovieService.controllers;

import com.example.MovieService.models.dtos.UserRegistrationDto;
import com.example.MovieService.repositories.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    private static final Logger logger = Logger.getLogger(AuthController.class);

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        logger.info("Showing registration form");

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
        try {
            logger.info(String.format("Registering user: %s", userRegistrationDto.getUsername()));

            userService.save(userRegistrationDto, userRegistrationDto.getRole());

            return "redirect:/login";
        } catch (Exception e) {
            logger.error(String.format("Error registering user: %s", userRegistrationDto.getUsername(), e));
            return "error";
        }
    }
}

