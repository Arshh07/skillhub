package com.skillhub.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.skillhub.hub.model.Gig;
import com.skillhub.hub.model.User;
import com.skillhub.hub.service.GigService;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/gigs")
public class GigController {
    private final GigService gigService;

    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    @GetMapping
    public String list(@RequestParam(value="q", required=false) String q, Model model) {
        if (q != null && !q.isEmpty()) {
            model.addAttribute("gigs", gigService.search(q));
        } else {
            model.addAttribute("gigs", gigService.all());
        }
        return "gigs";
    }

    @GetMapping("/create")
    public String createForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"FREELANCER".equals(user.getRole())) {
            model.addAttribute("error", "Only freelancers can create gigs. Login as FREELANCER.");
            return "gigs";
        }
        model.addAttribute("gig", new Gig());
        return "gig_form";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute Gig gig, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"FREELANCER".equals(user.getRole())) {
            model.addAttribute("error", "Only freelancers can create gigs.");
            return "gig_form";
        }
        // create a lightweight User object to set as freelancer (id must exist)
        User freelancer = new User();
        freelancer.setId(user.getId());
        gig.setFreelancer(freelancer);
        gigService.create(gig);
        return "redirect:/gigs";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Gig> g = gigService.findById(id);
        if (g.isEmpty()) {
            model.addAttribute("error", "Gig not found");
            return "gigs";
        }
        model.addAttribute("gig", g.get());
        return "gig_detail";
    }
}
