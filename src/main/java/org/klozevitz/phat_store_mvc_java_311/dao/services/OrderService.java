package org.klozevitz.phat_store_mvc_java_311.dao.services;

import jakarta.transaction.Transactional;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;

public interface OrderService extends DAO<Order> {

    Order findCartByUserId(Integer userId);
    @Transactional
    void addToCart(String email, String color, String size, Integer itemId);
}
