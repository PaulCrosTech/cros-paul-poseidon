package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("app")
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

    @GetMapping("login")
    public ModelAndView login() {
        log.info("====> GET /app/login <====");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        log.info("====> GET /app/secure/article-details <====");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

//    @GetMapping("error")
//    public ModelAndView error() {
//        log.info("====> GET /app/error <====");
//        ModelAndView mav = new ModelAndView();
//        String errorMessage = "You are not authorized for the requested data.";
//        mav.addObject("errorMsg", errorMessage);
//        mav.setViewName("error/403");
//        return mav;
//    }
}
