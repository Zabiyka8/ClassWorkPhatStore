package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderPositionService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.klozevitz.phat_store_mvc_java_311.repositories.OrderPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPositionServiceImplementation implements OrderPositionService {
    private final OrderPositionRepository repo;


    @Override
    public List<OrderPosition> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<OrderPosition> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public OrderPosition save(OrderPosition orderPosition) {
        return repo.save(orderPosition);
    }

    @Override
    public void deleteById(int id) {

    }
}
