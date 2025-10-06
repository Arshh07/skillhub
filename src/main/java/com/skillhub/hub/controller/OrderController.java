package com.skillhub.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.skillhub.hub.model.Gig;
import com.skillhub.hub.model.Order;
import com.skillhub.hub.model.User;
import com.skillhub.hub.service.GigService;
import com.skillhub.hub.service.OrderService;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final GigService gigService;

    public OrderController(OrderService orderService, GigService gigService) {
        this.orderService = orderService;
        this.gigService = gigService;
    }

    @PostMapping("/create/{gigId}")
    public String createOrder(@PathVariable Long gigId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "Login required");
            return "login";
        }
        Optional<Gig> g = gigService.findById(gigId);
        if (g.isEmpty()) {
            model.addAttribute("error", "Gig not found");
            return "gigs";
        }
        Gig gig = g.get();
        Order order = new Order();
        // create minimal client & freelancer references (IDs only)
        User client = new User();
        client.setId(user.getId());
        User freelancer = new User();
        freelancer.setId(gig.getFreelancer().getId());

        order.setClient(client);
        order.setFreelancer(freelancer);
        order.setGig(gig);
        order.setAmount(gig.getPrice());
        order.setStatus("PENDING");
        orderService.create(order);
        return "redirect:/orders/my";
    }

    @GetMapping("/my")
    public String myOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        switch (user.getRole()) {
            case "CLIENT" -> model.addAttribute("orders", orderService.findByClientId(user.getId()));
            case "FREELANCER" -> model.addAttribute("orders", orderService.findByFreelancerId(user.getId()));
            default -> model.addAttribute("orders", orderService.all());
        }
        return "orders";
    }
}
