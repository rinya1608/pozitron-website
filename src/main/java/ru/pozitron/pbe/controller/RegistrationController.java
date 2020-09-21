package ru.pozitron.pbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pozitron.pbe.domain.Code;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.repository.CodeRepository;
import ru.pozitron.pbe.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final CodeRepository codeRepository;

    public RegistrationController(UserService userService, CodeRepository codeRepository) {
        this.userService = userService;
        this.codeRepository = codeRepository;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("validErrors",errors.values());

            return "registration";
        }

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

    @GetMapping("/recoveryPassword")
    public String getRecoveryPassword(){
        return "recoveryPass";
    }

    @GetMapping("/message")
    public String messagePage(){
        return "messagePage";
    }

    @PostMapping("/recoveryPassword")
    public String recoveryPassword(Model model,String email){
        if (userService.createCodeAndSendMessageForPasswordRecovery(email)){
            model.addAttribute("message","Вам на почту отправлено сообщение c инструкциями");
           return "messagePage";
        }
        model.addAttribute("message","аккаунт не был активирован или аккаунта с таким e-mail адресом не существует");
        return "recoveryPass";
    }
    @GetMapping("/recoveryPassword/{code}")
    public String getRecoveryPasswordMessage(@PathVariable String code,Model model){
        Code codeForRecovery = codeRepository.findByValue(code);
        if( codeForRecovery != null){
            model.addAttribute("message","Ваш новый пароль"
                    + userService.setAndGetUUIDPassword(codeForRecovery.getUser()));
        }
        else model.addAttribute("message","ошибка");
        return "messagePage";
    }
}
