package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){

        if (!userService.addUser(user)){
            model.addAttribute("message","Пользователь уже зарегестрирован");
            return "regestration";
        }

        return "redirect:/login";
    }

    @GetMapping("activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.checkCodeAndActivateUser(code);
        if (isActivated){
            model.addAttribute("message","Аккаунт активирован");
        }
        else {
            model.addAttribute("message","Код активации не найден");
        }
        return "login";
    }

}
