package org.spring.myfilesafe.controller;

import jakarta.validation.Valid;
import org.spring.myfilesafe.entity.User;
import org.spring.myfilesafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/registration")
    public String registrationGet(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registrationPost(@Valid @ModelAttribute User user,
                                   BindingResult bindingResult,
                                   @RequestParam String confirmPassword,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("message", "Пароли не совпадают");
            return "registration";
        }
        userService.saveUser(user);
        return "authorisation";
    }

    @GetMapping(value = "/authorisation")
    public String authorisationGet() {
        return "authorisation";
    }

    @PostMapping(value = "/authorisation")
    public String authorisationPost(@RequestParam String login,
                                    @RequestParam String password,
                                    Model model){
        User user = userService.getUserByUsernameAndPassword(login, password);
        if(user == null){
            model.addAttribute("massage", "Не верны логин или пароль");
            return "redirect:/authorisation";
        }
        String username = user.getUsername();
        return "redirect:/upload?username=" + username;
    }

}
