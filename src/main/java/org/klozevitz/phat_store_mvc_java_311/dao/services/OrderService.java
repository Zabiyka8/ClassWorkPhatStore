package org.klozevitz.phat_store_mvc_java_311.dao.services;

import jakarta.transaction.Transactional;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;

import java.util.List;

public interface OrderService extends DAO<Order> {
    Order findCartByUserId(Integer userId);
    @Transactional
    void addToCart(String email, String color, String size, Integer itemId);
    Order findByOrderPositionId(int orderPositionId);
    @Transactional
    void pay(Order cart);
    List<Order> ordersToDeliver();
    void deliver(int orderId);
}
