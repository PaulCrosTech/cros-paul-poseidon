package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User Controller
 */
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
     * Display the user list page (except the current user)
     *
     * @param model the model
     * @return the user list page
     */
    @GetMapping("/list")
    public String home(Model model, @AuthenticationPrincipal User user) {
        log.info("====> GET /user/list <====");
        model.addAttribute("users", userService.findAllExceptUserWithUsername(user.getUsername()));
        return "user/list";
    }

    /**
     * Display the form for creating a new user.
     *
     * @return the user add page
     */
    @GetMapping("/add")
    public String addUser(Model model) {
        log.info("====> GET /user/add <====");
        model.addAttribute("userDto", new UserDto());
        return "user/add";
    }


    /**
     * Validate the form for creating a new user
     *
     * @param userDto            the user to create
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return redirect to the user list page
     */
    @PostMapping("/validate")
    public String validate(@Valid UserDto userDto, BindingResult result, RedirectAttributes redirectAttributes) {
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
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "User created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/user/list";
    }


    /**
     * Display the form for updating a user
     *
     * @param id                 the id of the user to update
     * @param model              the model
     * @param redirectAttributes the redirect attributes
     * @return the user update page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes
    ) {
        log.info("====> GET /user/update/{} <====", id);

        try {
            UserDto userDto = userService.findById(id);
            model.addAttribute("userDto", userDto);

        } catch (UserNotFoundException e) {
            log.error("====> GET /user/update/{} : user with id {} not found <====", id, e.getMessage());
            FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_DANGER, "An error occurred. Please try again later.");
            redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        }
        return "user/update";
    }

    /**
     * Validate the form for updating a user
     *
     * @param id                 the id of the user to update
     * @param userDto            the user to update
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return redirect to the user list page
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                             @Valid UserDto userDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        log.info("====> POST /user/update/{} <====", id);
        if (result.hasErrors()) {
            return "user/update";
        }

        try {
            userService.updateUser(userDto);
        } catch (UserWithSameUserNameExistsException e) {
            log.debug("====> POST /user/update/{} : exception while updating user  {} <====", id, e.getMessage());
            result.rejectValue("username", "error.userDto", e.getMessage());
            return "user/update";
        }

        log.info("====> POST /user/update/{} : user is updated <====", id);
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "User updated successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/user/list";
    }

    /**
     * Delete a user
     *
     * @param id the id of the user to delete
     * @return redirect to the user list page
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        log.info("====> GET /user/delete/{} <====", id);
        try {
            userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            log.error("====> GET /user/delete/{} : user with id {} not found <====", id, e.getMessage());
            FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_DANGER, "An error occurred. Please try again later.");
            redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
            return "redirect:/user/list";
        }

        log.info("====> POST /user/delete/{} : user is deleted <====", id);
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "User deleted successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/user/list";
    }
}
