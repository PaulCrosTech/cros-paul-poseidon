package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller for login
 */
@Controller
@Slf4j
@RequestMapping(path = "/app")
public class LoginController {


    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param userRepository userRepository
     */
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Display the login page
     *
     * @return the login page
     */
    @GetMapping("login")
    public ModelAndView login() {
        log.info("====> GET /app/login <====");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Display the article-details page (similar to the user list page)
     *
     * @return the article-details page
     */
    // TODO : url sécurisée mais non accessible depuis l'interface, voir pour la supprimer si inutile
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("====> GET /app/secure/article-details <====");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }


    /**
     * Display the error page (403)
     *
     * @return the error page (403)
     */
    // TODO : Gestion erreur 403 - possibilité de remplacer par le méthode standard de spring security (cf. SpringSecurityConfig)
    @GetMapping("error")
    public ModelAndView error() {
        log.info("====> GET /app/error <====");
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

}
