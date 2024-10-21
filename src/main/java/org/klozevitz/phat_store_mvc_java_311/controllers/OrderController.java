package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ApplicationUserService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ApplicationUserService applicationUserservice;

    @PostMapping("/pay")
    public String pay() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        ApplicationUser currentUser = applicationUserservice.loadUserByUsername(email);
        Order orderToPay = currentUser.getProfile().getOrders().stream()
                .filter(o -> !o.getIsPaid())
                .findFirst().get();

        double price = orderToPay.getPrice();

        if (currentUser.getProfile().pay(price)) {

        }
        return "redirect:/";
    }
}

/**
 * 0) выяснить, куда вставить проверку на наличие товаров из чека на складе
 * 1) релаизовать эту проверку (подумать, как донести пользоватлею, в случае провала)
 * 2) изменить статус заказа
 * 3) забрать все товары (stock position) со склада
 * 4) не забыть создать юзеру новую корзину
 * 5) ** сделать задание в ООП-стиле
 * */