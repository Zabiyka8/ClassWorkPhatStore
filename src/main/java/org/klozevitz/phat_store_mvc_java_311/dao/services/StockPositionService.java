package org.klozevitz.phat_store_mvc_java_311.dao.services;

import jakarta.transaction.Transactional;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;

import java.util.Optional;

public interface StockPositionService extends DAO<StockPosition> {
    StockPosition findStockPositionByColorAndSizeAndItemId(String color, String size, Integer itemId);

    @Transactional
    Optional<StockPosition> getOnePosition(String color, String size, Integer itemId);

    void remove(StockPosition position, int amount);
}
