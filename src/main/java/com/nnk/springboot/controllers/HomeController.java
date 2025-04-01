package com.nnk.springboot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Home controller
 */
@Controller
@Slf4j
public class HomeController {

    /**
     * Display the home page
     *
     * @return the home page
     */
    @GetMapping("/")
    public String home() {
        log.info("====> GET / <====");
        return "home";
    }

    /**
     * Display the admin home page
     *
     * @return the admin home page
     */
    // TODO : url sécurisée mais non accessible depuis l'interface, voir pour la supprimer si inutile
    @GetMapping("/admin/home")
    public String adminHome() {
        log.info("====> GET /admin/home redirect to /bidList/list <====");
        return "redirect:/bidList/list";
    }


}
