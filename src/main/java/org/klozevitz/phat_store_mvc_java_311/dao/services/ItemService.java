package org.klozevitz.phat_store_mvc_java_311.dao.services;

import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;

import java.util.List;

public interface ItemService extends DAO<Item> {
    List<Item> filterByCategoryIdAndPriceBetween(int categoryId, double minPrice, double maxPrice);
    List<Item> filterByBrandIdAndPriceBetween(int brandId, double minPrice, double maxPrice);
    List<Item> filterByCategoryIdAndBrandIdAndPriceBetween(int categoryId, int brandId, double minPrice, double maxPrice);
    Double maxPrice();
    Double minPrice();
    List<Item> filterByCategoryId(int categoryId);
    List<Item> filterByBrandId(int brandId);
}
