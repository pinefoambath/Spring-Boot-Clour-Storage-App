package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;


@Controller
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    private UserService userService;
    private AuthenticationService authenticationService;
    public LoginController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
    @GetMapping("/login")
    public String getHomePage()
    {
        return "login";
    }
//    @PostMapping("/postLogin")
//    public String postLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws Exception {
//        System.out.println("HELLO LOGIN");
//        System.out.println("PRINT:" + username);
//        User user = userService.loadUserByUsername(username);
//        Authentication result = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(result);
//        if (user != null) {
//            System.out.println("HERE I");
//            session.setAttribute("loggeduser", user);
//        } else {
//            System.out.println("HERE II");
//            session.setAttribute("loggeduser", username);
//        }
//        return "redirect:/home";
    }
