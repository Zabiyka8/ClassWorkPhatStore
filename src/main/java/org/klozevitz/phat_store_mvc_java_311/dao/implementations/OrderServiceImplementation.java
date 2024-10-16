package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderPositionService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.repositories.OrderPositionRepository;
import org.klozevitz.phat_store_mvc_java_311.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository repo;


    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Order findCartByUserId(Integer userId) {
        return repo.findCartByUserId(userId);

    }

    @Override
    public void addToCart(Integer userId, OrderPosition orderPosition) {
        Order cart = findCartByUserId(userId);
        cart.getPositions().add(orderPosition);
        repo.save(cart);
    }
}
