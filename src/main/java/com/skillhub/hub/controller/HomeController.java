package com.skillhub.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.skillhub.hub.service.GigService;

@Controller
public class HomeController {
    private final GigService gigService;

    public HomeController(GigService gigService) { this.gigService = gigService; }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("gigs", gigService.all());
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("gigs", gigService.all());
        return "dashboard";
    }
}
