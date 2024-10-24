package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final OrderService orderService;

    @GetMapping
    public String managersPage(Model model) {
        List<Order> ordersToDeliver = orderService.ordersToDeliver();
        model.addAttribute("orders", ordersToDeliver);
        return "ui/pages/manager/managerHomePage";
    }

    @PostMapping("/deliver")
    public String deliver(@RequestParam int orderId) {
        orderService.deliver(orderId);
        return "redirect:/manager";
    }

    @PostMapping("/decline")
    public String decline(@RequestParam int orderId) {

        return "redirect:/manager";
    }
}
