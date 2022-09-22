package co.develhope.Login.auth.controller;

import co.develhope.Login.auth.entities.LoginDTO;
import co.develhope.Login.auth.entities.LoginRTO;
import co.develhope.Login.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginRTO login(@RequestBody LoginDTO loginDTO){

        return loginService.login(loginDTO);
    }

}
