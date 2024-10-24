package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.StockPositionService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;
import org.klozevitz.phat_store_mvc_java_311.repositories.StockPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockPositionServiceImplementation implements StockPositionService {
    private final StockPositionRepository repo;

    @Override
    public List<StockPosition> findAll() {
        return null;
    }

    @Override
    public Optional<StockPosition> findById(int id) {
        return Optional.empty();
    }

    @Override
    public StockPosition save(StockPosition stockPosition) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public StockPosition findStockPositionByColorAndSizeAndItemId(String color, String size, Integer itemId) {
        return repo.findStockPositionByColorAndSizeAndItemId(color, size, itemId);
    }

    @Override
    public Optional<StockPosition> getOnePosition(String color, String size, Integer itemId) {
        // по переданным данным мы ищем товар на складе
        StockPosition stockPositionToBuy = repo.findStockPositionByColorAndSizeAndItemId(color, size, itemId);
        if (stockPositionToBuy.getAmount() > 0) {
            // сохранение "полки" с данным товаром
            return Optional.of(repo.save(stockPositionToBuy));
        }
        return Optional.empty();
    }

    @Override
    public void remove(StockPosition position, int amount) {
        position.setAmount(position.getAmount() - amount);
        repo.save(position);
    }
}
