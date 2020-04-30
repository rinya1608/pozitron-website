package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.domain.Role;
import ru.pozitron.pbe.domain.User;
import ru.pozitron.pbe.repository.CategoryRepository;
import ru.pozitron.pbe.repository.UserRepository;
import ru.pozitron.pbe.service.UserService;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    CategoryRepository categoryRepository;

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
    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal User user,Model model){
        Iterable<Category> categories = categoryRepository.findAllByParentCategory(null);
        model.addAttribute("user",user);
        model.addAttribute("categories",categories);
        return "userProfile";
    }
    @PostMapping("/profile")
    public String updateUserProfile(@AuthenticationPrincipal User user,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String username,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) String number,
                                    Model model){

        if (name != null) model.addAttribute("nameMessage",userService.updateUserName(user,name));
        if (username != null) model.addAttribute("usernameMessage",userService.updateUserUsername(user,username));
        if (email != null) model.addAttribute("emailMessage",userService.updateUserEmail(user,email));
        if (number != null) model.addAttribute("numberMessage",userService.updateUserNumber(user,number));
        userRepository.save(user);
        Iterable<Category> categories = categoryRepository.findAllByParentCategory(null);
        model.addAttribute("user",user);
        model.addAttribute("categories",categories);
        return "userProfile";
    }
}
