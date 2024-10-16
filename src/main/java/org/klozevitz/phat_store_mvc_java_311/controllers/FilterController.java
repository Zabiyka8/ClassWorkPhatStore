package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.BrandService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.CategoryService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ItemService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Brand;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Category;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ItemService itemService;

    @GetMapping
    public String filter(Model model, @RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false) Integer brandId,
                                     @RequestParam(required = false) Double minPrice,
                                     @RequestParam(required = false) Double maxPrice){

        if (minPrice == null && brandId != null){
            filterByBrandId(model, brandId);
        }else if(categoryId != null && minPrice == null){
            filterByCategoryId(model, categoryId);
        }else {
            switchFilter(model, categoryId, brandId, minPrice, maxPrice);
        }

        return "ui/pages/filterResult";
    }
    private void filterByCategoryIdAndPriceBetween(Model model, int categoryId, Double minPrice, Double maxPrice){
         List<Item> items = itemService.filterByCategoryIdAndPriceBetween(categoryId,minPrice,maxPrice)
                .stream()
                .filter(item -> item.isInStock())
                .toList();
        model.addAttribute("items", items);
    }
    private void filterByBrandIdAndPriceBetween(Model model, int brandId, Double minPrice, Double maxPrice){
        List<Item> items = itemService.filterByBrandIdAndPriceBetween(brandId,minPrice,maxPrice)
                .stream()
                .filter(item -> item.isInStock())
                .toList();

        model.addAttribute("items", items);
    }
    private void filterByCategoryIdAndBrandIdAndPriceBetween(Model model, int brandId, int categoryId, Double minPrice, Double maxPrice){
        List<Item> items = itemService.filterByCategoryIdAndBrandIdAndPriceBetween(categoryId, brandId, minPrice, maxPrice)
                .stream()
                .filter(item -> item.isInStock())
                .toList();

        model.addAttribute("items", items);
    }
    public void switchFilter(Model model, Integer categoryId, Integer brandId, Double minPrice, Double maxPrice){
        if(categoryId == null && brandId != null){
            filterByBrandIdAndPriceBetween(model, brandId, minPrice,maxPrice);
            }
        if (categoryId != null && brandId == null){
            filterByCategoryIdAndPriceBetween(model, categoryId, minPrice, maxPrice);
            }
        if(categoryId != null && brandId != null){
            filterByCategoryIdAndBrandIdAndPriceBetween(model, categoryId, brandId, minPrice, maxPrice);
            }
    }

    @GetMapping("/page")
    public String pages(Model model){
        List<Brand> allBrand = brandService.findAll();
        model.addAttribute("brands", allBrand);

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("categories", allCategories);

        Double minPrice = itemService.minPrice();
        model.addAttribute("minPrice", minPrice);

        Double maxPrice = itemService.maxPrice();
        model.addAttribute("maxPrice", maxPrice);

        return "ui/pages/search";
    }



    private void filterByCategoryId(Model model, int categoryId){
        List<Item> items = itemService.filterByCategoryId(categoryId)
                .stream()
                .filter(item -> item.isInStock())
                .toList();
        model.addAttribute("items", items);
    }
    private void filterByBrandId(Model model, int brandId){
        List<Item> items = itemService.filterByBrandId(brandId)
                .stream()
                .filter(item -> item.isInStock())
                .toList();
        model.addAttribute("items", items);
    }


}
