package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import java.util.List;

@Controller
public class CredentialsController {

    private UserService userService;
    private CredentialForm credentialForm;
    private Credential credential;

    @Autowired
    CredentialService credentialService;

    @Autowired
    UserMapper userMapper;

    // VIEW or EDIT
        @PostMapping("/credentials")
        public String update(Authentication authentication, CredentialForm credentialForm, Model model) throws Exception {
            String username = authentication.getName();
            User user = userMapper.getUser(username);
            Integer userId = user.getUserId();
            credentialForm.setUserId(userId);
            Credential credential = new Credential(credentialForm.getCredentialId(), credentialForm.getUrl(),credentialForm.getUserName(), credentialForm.getKey(), credentialForm.getPassword(), credentialForm.getUserId());
        //check if there are any credentials to update, otherwise insert new credentials
        if(credential.getCredentialId() == 0) {
            this.credentialService.insert(credential);
        } else {
            this.credentialService.update(credential);
        }
        model.addAttribute("credentialForm", new CredentialForm());
        List<Credential> credentials = credentialService.getCredentialsByUserId(user.getUserId());
        model.addAttribute("credentials", credentials);
        return "/home";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") String credentialId) {
            if (Integer.parseInt(credentialId) > 0) {
                credentialService.delete(Integer.parseInt(credentialId));
                return "redirect:/home";
            }
        return "redirect:/home";
    }


}
