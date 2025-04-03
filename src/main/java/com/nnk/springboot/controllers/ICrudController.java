package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.AbstractDto;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Interface for CRUD controllers.
 *
 * @param <Dto> the type of the DTO
 */
public interface ICrudController<Dto extends AbstractDto> {


    /**
     * Add attributes to the model, managing the menu activation.
     *
     * @param model the model to add attributes to
     */
    @ModelAttribute
    void addAttributes(Model model);

    /**
     * Display the list of all items.
     *
     * @param model the model to add attributes to
     * @return the view name
     */
    @GetMapping(path = "/list")
    String home(Model model);

    /**
     * Display the form to add a new item.
     *
     * @param model the model to add attributes to
     * @return the view name
     */
    @GetMapping(path = "/add")
    String addForm(Model model);

    /**
     * Validate the addition of a new item.
     *
     * @param dto                the DTO to validate
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @PostMapping(path = "/validate")
    String addValidate(
            @Valid @ModelAttribute("dto") Dto dto,
            BindingResult result,
            RedirectAttributes redirectAttributes);

    /**
     * Display the form to update an existing item.
     *
     * @param id                 the ID of the item to update
     * @param model              the model to add attributes to
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @GetMapping(path = "/update/{id}")
    String updateForm(@PathVariable("id") Integer id,
                      Model model,
                      RedirectAttributes redirectAttributes);

    /**
     * Validate the update of an existing item.
     *
     * @param id                 the ID of the item to update
     * @param dto                the DTO to validate
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @PostMapping(path = "/update/{id}")
    String updateValidate(@PathVariable("id") Integer id,
                          @Valid @ModelAttribute("dto") Dto dto,
                          BindingResult result,
                          RedirectAttributes redirectAttributes);

    /**
     * Display the form to delete an item.
     *
     * @param id                 the ID of the item to delete
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @GetMapping(path = "/delete/{id}")
    String delete(@PathVariable("id") Integer id,
                  RedirectAttributes redirectAttributes);

}
