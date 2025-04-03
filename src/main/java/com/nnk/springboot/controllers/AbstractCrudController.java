package com.nnk.springboot.controllers;


import com.nnk.springboot.dto.AbstractDto;
import com.nnk.springboot.dto.AlertClass;
import com.nnk.springboot.dto.FlashMessage;
import com.nnk.springboot.exceptions.EntityMissingException;
import com.nnk.springboot.service.ICrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Abstract controller for CRUD operations
 *
 * @param <Dto>     extends AbstractDto
 * @param <Service> extends ICrudService<Dto>
 */
@Slf4j
public abstract class AbstractCrudController<Dto extends AbstractDto, Service extends ICrudService<Dto>>
        implements ICrudController<Dto> {

    protected final Service service;
    private final Class<Dto> dtoClass;
    protected final String route;

    /**
     * Constructor
     *
     * @param service  the service
     * @param route    the name of the route (without the leading slash)
     * @param dtoClass the class of the DTO
     */
    public AbstractCrudController(Service service, String route, Class<Dto> dtoClass) {
        this.service = service;
        this.route = route;
        this.dtoClass = dtoClass;
    }


    /**
     * Adds the menu activated attribute to the model
     *
     * @param model the model
     */
    @Override
    public void addAttributes(Model model) {
        model.addAttribute("menuActivated", route);
    }

    /**
     * Display the list of all items.
     *
     * @param model the model
     */
    @Override
    public String home(Model model) {
        log.info("====> GET /{}/list <====", route);

        List<Dto> dtoList = service.findAll();
        model.addAttribute("dtoList", dtoList);

        return route + "/list";
    }

    /**
     * Display the form to add a new item.
     *
     * @param model the model to add attributes to
     * @return the view name
     */
    @Override
    public String addForm(Model model) {
        log.info("====> GET /{}/add <====", route);
        try {
            Dto dtoInstance = dtoClass.getDeclaredConstructor().newInstance();
            model.addAttribute("dto", dtoInstance);
        } catch (Exception e) {
            log.error("====> Erreur lors de la création de l'instance de {}  : {}", dtoClass.getName(), e.getMessage());
            throw new RuntimeException("Impossible de créer une instance du DTO", e);
        }

        return route + "/add";
    }

    /**
     * Validate the addition of a new item.
     *
     * @param dto                the DTO to validate
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @Override
    public String addValidate(Dto dto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        log.info("====> POST /{}/validate <====", route);

        if (result.hasErrors()) {
            log.debug("====> form contains error <====");
            return route + "/add";
        }

        service.create(dto);

        log.info("====>  created successfully <====");
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Created successfully");
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/" + route + "/list";
    }

    /**
     * Display the form to update an existing item.
     *
     * @param id                 the ID of the item to update
     * @param model              the model to add attributes to
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @Override
    public String updateForm(Integer id,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        log.info("====> GET /{}/update/{} <====", route, id);

        try {
            AbstractDto dto = service.findById(id);
            model.addAttribute("dto", dto);
        } catch (EntityMissingException e) {
            log.error("====> Error : {} <====", e.getMessage());
            redirectAttributes.addFlashAttribute("flashMessage", new FlashMessage());
            return "redirect:/" + route + "/list";
        }

        return route + "/update";
    }

    /**
     * Validate the update of an existing item.
     *
     * @param id                 the ID of the item to update
     * @param dto                the DTO to validate
     * @param result             the binding result
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @Override
    public String updateValidate(Integer id,
                                 Dto dto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        log.info("====> POST /{}/update/{} <====", route, id);

        if (result.hasErrors()) {
            return route + "/update";
        }

        String logMessage = "Updated successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Updated successfully");

        try {
            service.update(dto);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/" + route + "/list";
    }

    /**
     * Delete an item.
     *
     * @param id                 the ID of the item to delete
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @Override
    public String delete(Integer id,
                         RedirectAttributes redirectAttributes) {

        log.info("====> GET /{}/delete/{} <====", route, id);


        String logMessage = "Deleted successfully";
        FlashMessage flashMessage = new FlashMessage(AlertClass.ALERT_SUCCESS, "Deleted successfully");

        try {
            service.delete(id);
        } catch (EntityMissingException e) {
            logMessage = e.getMessage();
            flashMessage = new FlashMessage();
        }

        log.info("====> {} <====", logMessage);
        redirectAttributes.addFlashAttribute("flashMessage", flashMessage);
        return "redirect:/" + route + "/list";
    }
}