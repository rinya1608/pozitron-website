package ru.pozitron.pbe.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pozitron.pbe.domain.Role;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.repository.UserRepository;
import ru.pozitron.pbe.service.UserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "userList";
    }
    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(@PathVariable User user,Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String,String> form,
            @RequestParam("userId") User user
    ){
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
    @GetMapping(value = {"/profile","/profile/*"})
    public String userProfile(@AuthenticationPrincipal User user,Model model){
        user = userRepository.getOne(user.getId());
        model.addAttribute("user",user);
        return "userProfile";
    }
    @PostMapping("/profile/activate")
    public String sendActivateCode(@AuthenticationPrincipal User user,Model model){
        userService.resendActivateCode(user);
        model.addAttribute("message","На указанный вами e-mail адрес отправлена инструкция по активации аккаунта");
        return "messagePage";
    }

    @PostMapping("/profile")
    public String updateUserProfile(@AuthenticationPrincipal User user,
                                    @Valid User validUser,
                                    BindingResult bindingResult,
                                    Model model){
        user = userRepository.getOne(user.getId());
        Map<String,String> errors = new HashMap<>();

        if (bindingResult.hasErrors()){
            errors = ControllerUtils.getErrors(bindingResult);
            errors.remove("passwordError");
        }

        if (!errors.isEmpty()){
            if (!user.isActive())
                errors.put("activateError","что бы вносить изменения активируйте аккаунт");

            model.addAttribute("validErrors",errors.values());
            model.addAttribute("user",user);
            return "userProfile";
        }
        user.setName(validUser.getName());
        if(!validUser.getSurname().isEmpty()) user.setSurname(validUser.getSurname());
        user.setUsername(validUser.getUsername());
        if (!validUser.getEmail().equals(user.getEmail())){
            model.addAttribute("emailMessage",userService.updateUserEmail(user,validUser.getEmail()));
        }
        if(!validUser.getNumber().isEmpty()) user.setNumber(validUser.getNumber());
        userRepository.save(user);
        model.addAttribute("user",user);
        return "userProfile";
    }
    @GetMapping("/profile/changeEmail/{code}")
    public String changeEmail(Model model, @PathVariable String code){
        boolean isTrueCode = userService.checkCodeAndActivateUser(code);
        if (isTrueCode){
            model.addAttribute("emailMessage","Ваш e-mail адрес изменен");
        }
        else model.addAttribute("emailMessage","Ссылка не действительна");
        return "redirect:/user/profile";
    }

    @GetMapping("/profile/changePassword")
    public String changePasswordPage(){
        return "changePass";
    }
    @PostMapping("/profile/changePassword")
    public String changePassword(@AuthenticationPrincipal User user,String oldPassword,String newPassword,Model model){
        if (userService.updatePassword(user,oldPassword,newPassword)){
            model.addAttribute("passwordMessage","пароль изменен");
        }
        else {
            model.addAttribute("error","аккаунт не активирован или старый пароль был введен не верно");
        }

        return "changePass";
    }
}
