package org.klozevitz.phat_store_mvc_java_311.dao.services;

import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;

public interface OrderService extends DAO<Order> {

    Order findCartByUserId(Integer userId);
    void addToCart(Integer userId, OrderPosition orderPosition);
}
