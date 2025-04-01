package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.service.impl.UserService;
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

    private final UserService userService;

    /**
     * Constructor
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
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
     * @param user  the current user
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
     * @param model the model
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
            log.debug("====> form contains error <====");
            return "user/add";
        }

        try {
            userService.create(userDto);
        } catch (UserWithSameUserNameExistsException e) {
            log.debug("====> Error : {} <====", e.getMessage());
            result.rejectValue("username", "error.userDto", e.getMessage());
            return "user/add";
        }

        log.info("====> User created successfully <====");
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
        } catch (EntityMissingException e) {
            log.error("====> Error : {} <====", e.getMessage());
            redirectAttributes.addFlashAttribute("flashMessage", new FlashMessage());
            return "redirect:/user/list";
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

        String logMessage = "User updated successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "User updated successfully");

        try {
            userService.update(userDto);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        } catch (UserWithSameUserNameExistsException e) {
            log.debug("====> Error : {} <====", e.getMessage());
            result.rejectValue("username", "error.userDto", e.getMessage());
            return "user/update";
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/user/list";
    }


    /**
     * Delete a user
     *
     * @param id                 the id of the user to delete
     * @param redirectAttributes the redirect attributes
     * @return redirect to the user list page
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        log.info("====> GET /user/delete/{} <====", id);

        String logMessage = "User deleted successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "User deleted successfully");

        try {
            userService.delete(id);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/user/list";
    }
}
