package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ApplicationUserService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderPositionService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Status;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class CartController {
    private final OrderService orderService;
    private final ApplicationUserService applicationUserservice;
    private final OrderPositionService orderPositionService;
    
    @GetMapping("/cart")
    public String cartPage(Model model) {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);
        model.addAttribute("cart", cart);
        return "ui/pages/cart";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public void addToCart(@RequestParam String color, @RequestParam String size, @RequestParam Integer itemId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.addToCart(email, color, size, itemId);
    }

    @PostMapping("/cart/delete")
    public String deletePosition(@RequestParam int positionId) {
        orderPositionService.deleteById(positionId);
        return "redirect:/order/cart";
    }

    @PostMapping("/cart/drop")
    public String deleteOneFromOrderPosition(@RequestParam int positionId) {
        OrderPosition orderPositionToUpdate = orderPositionService.findById(positionId).get();
        orderPositionToUpdate.setAmount(orderPositionToUpdate.getAmount() - 1);
        if (orderPositionToUpdate.getAmount() == 0) {
            orderPositionService.deleteById(positionId);
        } else {
            orderPositionService.save(orderPositionToUpdate);
        }
        return "redirect:/order/cart";
    }

    @PostMapping("/pay")
    public String pay() {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);

        double price = cart.getPrice();

        if (currentUser.getProfile().pay(price)) {

        }
        return "redirect:/";
    }

    private Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .findFirst().get();
    }

    private ApplicationUser currentApplicationUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return applicationUserservice.loadUserByUsername(email);
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



//@PostMapping("/cart/delete")
//public String deletePosition(@RequestParam int positionId) {
//        ApplicationUser currentUser = currentApplicationUser();
//        Order cart = currentUserCart(currentUser);
//
//        cart
//                .getPositions()
//                .removeIf(position -> position.getId() == positionId);
//
//        orderService.save(cart);
//    return "redirect:/order/cart";
//}
//
//    @PostMapping("/cart/drop")
//    public String deleteOneFromOrderPosition(@RequestParam int positionId) {
//        ApplicationUser currentUser = currentApplicationUser();
//        Order cart = currentUserCart(currentUser);
//
//        OrderPosition orderPositionToDeleteOneStockPosition = cart.getPositions().stream()
//                .filter(p -> p.getId() == positionId)
//                .findFirst().get();
//
//        orderPositionToDeleteOneStockPosition.setAmount(orderPositionToDeleteOneStockPosition.getAmount() - 1);
//
//        if (orderPositionToDeleteOneStockPosition.getAmount() == 0) {
//            cart.getPositions().remove(orderPositionToDeleteOneStockPosition);
//        }
//
//        orderService.save(cart);
//        return "redirect:/order/cart";
//    }
//}