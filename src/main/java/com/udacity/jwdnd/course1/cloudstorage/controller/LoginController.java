package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
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
    private AuthenticationService authenticationService;

    public LoginController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    public String postLogin(Model model, HttpSession session) {

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String loggedInUser = (authentication.getPrincipal().toString());

        User user = userService.getUser(loggedInUser);

        if (user != null) {

            session.setAttribute("loggeduser", user);

        } else {

            session.setAttribute("loggeduser", loggedInUser);

        }

        return "home";
    }

}