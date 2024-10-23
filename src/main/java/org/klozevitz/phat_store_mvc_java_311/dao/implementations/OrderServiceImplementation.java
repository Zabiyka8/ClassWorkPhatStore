package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.ApplicationUserService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderPositionService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.OrderService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.StockPositionService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.klozevitz.phat_store_mvc_java_311.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository repo;
    private final StockPositionService stockPositionService;
    private final ApplicationUserService applicationUserService;
    private final OrderPositionService orderPositionService;


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
    public Order findCartByUserId(Integer profileId) {
        return repo.findCartByUserId(profileId);

    }

    @Override
    public void addToCart(String email, String color, String size, Integer itemId) {
        ApplicationUser loggedUser = applicationUserService.loadUserByUsername(email);
        // по id юзера ищем корзину (единственный неоплаченный заказ)
        Order cart = findCartByUserId(loggedUser.getProfile().getId());
        // получаем 1 единицу товара со склада
        Optional<StockPosition> stockPositionToBuy = stockPositionService.getOnePosition(color, size, itemId);
        // на основе позиции со склада, которую собрался приобретать покупатель, формируем запись в чеке (корзине)
        if (stockPositionToBuy.isPresent()) {
        OrderPosition positionToAdd = OrderPosition.builder()
                .amount(1)
                .stockPosition(stockPositionToBuy.get())
                .order(cart)
                .build();

        addToCart(cart, positionToAdd);
        repo.save(cart);
        } else {
            throw new IllegalArgumentException("Такого товара нет в наличии");
        }
    }

    @Override
    public Order findByOrderPositionId(int orderPositionId) {
        return orderPositionService.findById(orderPositionId).get().getOrder();
    }

    private void addToCart(Order cart, OrderPosition positionToAdd) {
        // позиция на складе (товар) для дополнения корзины
        StockPosition stockPosition = positionToAdd.getStockPosition();
        // Если товар присутствует в корзине, изменяем его количество, в противном случае, добавляем новую строку в корзину
        if (isStockPositionPresent(cart, stockPosition)) {
            OrderPosition positionToIncrementAmount = cart.getPositions().stream()
                    .filter(orderPosition -> orderPosition.getStockPosition().getId() == stockPosition.getId())
                    .findFirst().get();
            positionToIncrementAmount.setAmount(positionToIncrementAmount.getAmount() + 1);
        } else {
            // добавляем запись в чек (корзину)
            cart.getPositions().add(positionToAdd);
        }
    }

    private boolean isStockPositionPresent(Order cart, StockPosition stockPosition) {
        return cart.getPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getStockPosition().getId() == stockPosition.getId());
    }
}
