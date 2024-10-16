package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ApplicationUserService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ItemService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderPositionService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final OrderPositionService orderPositionService;
    private final OrderService orderService;
    private final ApplicationUserService applicationUserService;

    @GetMapping
    public String itemPage(Model model,@RequestParam Integer itemId) {
        Optional<Item> item = itemService.findById(itemId);
        if (item.isPresent()){
            model.addAttribute("item", item.get());
        }

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "ui/pages/item";
    }

    @GetMapping("/addToCart")
    @ResponseBody
    public void addToCart(@RequestParam String color, @RequestParam String size, @RequestParam Integer itemId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderPosition orderPosition = orderPositionService.filterOrderPositionByColorAndSizeAndItemId(color, size, itemId);
        orderService.addToCart(applicationUserService.loadUserByUsername(email).getId(),orderPosition);
    }

}
