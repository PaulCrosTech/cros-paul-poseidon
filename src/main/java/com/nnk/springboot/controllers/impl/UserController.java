package com.nnk.springboot.controllers.impl;

import com.nnk.springboot.controllers.AbstractCrudController;
import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.exceptions.UserWithSameUserNameExistsException;
import com.nnk.springboot.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserController extends AbstractCrudController<UserDto, UserService> {

    private final UserService userService;
    private static final String ROUTE = "user";

    /**
     * Constructor
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
        super(userService, ROUTE, UserDto.class);
        this.userService = userService;
    }

    /**
     * Display the user list page (except the current user)
     *
     * @param model the model
     * @return the view name
     */
    @Override
    public String home(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("====> GET /{}/list <====", ROUTE);

        model.addAttribute("dtoList", userService.findAllExceptUserWithUsername(user.getUsername()));
        return ROUTE + "/list";

    }

    /**
     * Validate the form for creating a new user
     *
     * @param userDto            the user to create
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return redirect to the user list page
     */
    @Override
    public String addValidate(UserDto userDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        log.info("====> POST /{}/validate <====", ROUTE);

        if (result.hasErrors()) {
            log.debug("====> form contains error <====");
            return ROUTE + "/add";
        }

        try {
            userService.create(userDto);
        } catch (UserWithSameUserNameExistsException e) {
            log.debug("====> Error : {} <====", e.getMessage());
            result.rejectValue("username", "error.userDto", e.getMessage());
            return ROUTE + "/add";
        }

        log.info("====> User created successfully <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "User created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/" + ROUTE + "/list";
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
    @Override
    public String updateValidate(Integer id,
                                 UserDto userDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        log.info("====> POST /{}/update/{} <====", id, ROUTE);
        if (result.hasErrors()) {
            return ROUTE + "/update";
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
            return ROUTE + "/update";
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/" + ROUTE + "/list";
    }


}
