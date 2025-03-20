package com.nnk.springboot.controllers;

import com.nnk.springboot.entity.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository userRepository;
    private final IUserService userService;


    /**
     * Constructor
     *
     * @param userRepository userRepository
     */
    public UserController(UserRepository userRepository,
                          IUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Add generic attributes to the model
     *
     * @param model the model
     */
    @ModelAttribute
    private void addAttributes(Model model) {
        model.addAttribute("menuActivated", "user");
    }

    /**
     * Display the user list page.
     *
     * @param model the model
     * @return the user list page
     */
    @GetMapping("/list")
    public String home(Model model) {
        log.info("====> GET /user/list <====");
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }


    /**
     * Display the user add page.
     *
     * @return the user add page
     */
    @GetMapping("/add")
    public String addUser(Model model) {
        log.info("====> GET /user/add <====");
        model.addAttribute("userDto", new UserDto());
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid UserDto userDto, BindingResult result, Model model) {
        log.info("====> POST /user/validate <====");

        if (result.hasErrors()) {
            log.debug("====> POST /user/validate : form contains error <====");
            return "user/add";
        }
        
        try {
            userService.addUser(userDto);
        } catch (UserWithSameUserNameExistsException e) {
            log.debug("====> POST /user/validate : exception while creating user  {} <====", e.getMessage());
            result.rejectValue("username", "error.userDto", e.getMessage());
            return "user/add";
        }

        log.info("====> POST /user/validate : user is created <====");
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("====> GET /user/update/{} <====", id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        log.info("====> POST /user/update/{} <====", id);
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        log.info("====> GET /user/delete/{} <====", id);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
