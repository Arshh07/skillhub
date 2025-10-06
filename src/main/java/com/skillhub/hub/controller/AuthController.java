package com.skillhub.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.skillhub.hub.model.User;
import com.skillhub.hub.service.UserService;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) { this.userService = userService; }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        Optional<User> existing = userService.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        if (user.getRole() == null || user.getRole().isEmpty()) user.setRole("CLIENT");
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() { return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<User> u = userService.findByEmail(email);
        if (u.isEmpty()) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
        User user = u.get();
        if (!userService.checkPassword(user, password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
        // remove password before putting into session (but object still has hashed pwd)
        user.setPassword(null);
        session.setAttribute("user", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
