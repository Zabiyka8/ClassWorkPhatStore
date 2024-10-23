package org.klozevitz.phat_store_mvc_java_311.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ApplicationUserService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ItemService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderPositionService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Color;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Size;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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
            List<String> sizes = getSizesByCategory(item.get().getCategory().getId());
            model.addAttribute("sizes", sizes);
            model.addAttribute("colors", Arrays.stream(Color.values()).map(Enum::name));
        }
        return "ui/pages/item";
    }

    private List<String> getSizesByCategory(int categoryId) {
        if (categoryId == 1) {
            return Size.HATS.getSizes();
        }
        if (categoryId == 2) {
            return Size.CLOTHES.getSizes();
        }
        if (categoryId == 3) {
            return Size.SHOES.getSizes();
        }
        return null;
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public void addToCart(HttpServletRequest request, @RequestParam String color, @RequestParam String size, @RequestParam Integer itemId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.addToCart(email, color, size, itemId);
        System.out.println();
    }

}
