package org.klozevitz.phat_store_mvc_java_311.dao.services;

import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;

import java.util.List;

public interface OrderPositionService extends DAO<OrderPosition> {
    OrderPosition filterOrderPositionByColorAndSizeAndItemId(String color, String size, Integer itemId);
}
