package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/postLogin", method = RequestMethod.POST)

public class LoginController {

    private UserService userService;

    public String postLogin(Model model, HttpSession session) {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String loggedInUser = (authentication.getPrincipal().toString());

        User userDetails = userService.findByUsername(loggedInUser);

        if (userDetails != null) {

            session.setAttribute("loggeduser", userDetails);

        } else {

            session.setAttribute("loggeduser", loggedInUser);

        }

        return "home";
    }

}