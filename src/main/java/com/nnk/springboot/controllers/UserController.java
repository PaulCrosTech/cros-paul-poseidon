package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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

    /**
     * Constructor
     *
     * @param userRepository userRepository
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public String addUser(User bid) {
        log.info("====> GET /user/add <====");
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        log.info("====> POST /user/validate <====");
        if (!result.hasErrors()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
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
