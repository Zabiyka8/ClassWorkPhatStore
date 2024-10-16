package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ItemService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.klozevitz.phat_store_mvc_java_311.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImplementation implements ItemService {
    private final ItemRepository repo;
    @Override
    public List<Item> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Item> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Item save(Item item) {
        return repo.save(item);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Item> filterByCategoryIdAndPriceBetween(int categoryId, double minPrice, double maxPrice) {
        List<Item> items = repo.filterByCategoryIdAndPriceBetween(categoryId, maxPrice, minPrice);
        return items;
    }

    @Override
    public List<Item> filterByBrandIdAndPriceBetween(int brandId, double minPrice, double maxPrice) {
        return repo.filterByBrandIdAndPriceBetween(brandId, maxPrice, minPrice);
    }

    @Override
    public List<Item> filterByCategoryIdAndBrandIdAndPriceBetween(int categoryId, int brandId, double minPrice, double maxPrice) {
        return repo.filterByCategoryIdAndBrandIdAndPriceBetween(categoryId, brandId, maxPrice, minPrice);
    }


    @Override
    public List<Item> filterByCategoryId(int categoryId) {
        return repo.queryCategoryId(categoryId);
    }

    @Override
    public List<Item> filterByBrandId(int brandId) {
        return repo.queryBrandId(brandId);
    }


    @Override
    public Double maxPrice() {
        return repo.maxPrice();
    }

    @Override
    public Double minPrice() {
        return repo.minPrice();
    }
}
