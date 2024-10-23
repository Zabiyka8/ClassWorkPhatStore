package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ItemService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Color;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Size;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
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
}
