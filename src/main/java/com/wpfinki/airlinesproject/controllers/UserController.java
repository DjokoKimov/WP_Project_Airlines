package com.wpfinki.airlinesproject.controllers;

import com.wpfinki.airlinesproject.Exception.UserAlreadyExists;
import com.wpfinki.airlinesproject.Exception.UserNotFound;
import com.wpfinki.airlinesproject.model.User;
import com.wpfinki.airlinesproject.repository.UserRepository;
import com.wpfinki.airlinesproject.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/showReg")
    public String showRegistrationPage() {
        LOGGER.info("Inside showRegistrationPage()");
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String register(@ModelAttribute("user") User user) {
        //check and handle error if email already exists
        LOGGER.info("{} Inside register() " + user.getEmail());
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            LOGGER.error("User already exists with email: " + user.getEmail());
            throw new UserAlreadyExists("User with that email already exists:" + user.getEmail());
        }
        LOGGER.info("User exists:" + user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "displayFlights";
    }

    @GetMapping("/showLogin")
    public String showLoginPage() {
        LOGGER.info("Inside showLoginPage()");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password, Model model) {
        LOGGER.info("{} Inside login()" + email);
        Optional<User> foundUser = userRepository.findByEmail(email);
        //check and handle error if user with that email is not found
        if (!foundUser.isPresent()) {
            LOGGER.error("User with that email not found: " + email);
            throw new UserNotFound("No user found with that email: " + email);
        }
        LOGGER.info("Email exists"+email);
        boolean loginResponse= securityService.login(email,bCryptPasswordEncoder.encode(password));
        if(loginResponse){
        model.addAttribute("msg","Successfully logged in!");
        return "findFlights";
        }
        else {
            LOGGER.info("User entered invalid credentials email:{} and password:{}"+email,password);
            model.addAttribute("msg","Invalid username or password");
        }
        return "findFlights";
    }
}
