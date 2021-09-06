package ft.springjpa.controllers;

import ft.springjpa.entities.User;
import ft.springjpa.repositories.UserRepository;
import ft.springjpa.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("")
public class LoginController {

    @GetMapping("")
    public String dash(){
        return "dashboard.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}
