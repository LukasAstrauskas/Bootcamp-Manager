package com.bootcamp.bootcampmanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new-user")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        /* Replace with errors */
        if (userService.existsByEmail(user.getEmail())) {
            return "redirect:/repeating-emails/" + user.getEmail();
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/repeating-emails/{email}")
    public String repeatingEmail(Model model, @PathVariable("email") String email) {
        model.addAttribute("email", email);
        return "repeating-emails";
    }

    @GetMapping("/update-user/{id}")
    public String updateUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "update-user";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/change-password/{id}")
    public String newPasswordForm(@PathVariable("id") long userID, Model model) {
        model.addAttribute("dataContainer", new DataContainer(userID));
        return "change-password";
    }

    @PostMapping("/update-password")
    public String updatePassword(@ModelAttribute("dataContainer") DataContainer data) {
        userService.updateUserPassword(data);
        return "redirect:/users";
    }


    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/change-profile-password")
    public String newProfilePasswordForm(Model model, Authentication auth) {
        Long id = userService.getUserIDByEmail(auth.getName());
        model.addAttribute("dataContainer", new DataContainer(id));
        return "change-profile-password";
    }

    @PostMapping("/update-profile-password")
    public String updateProfilePassword(@ModelAttribute("dataContainer") DataContainer data) {
        userService.updateUserPassword(data);
        return "redirect:/profile";
    }
}