package org.klozevitz.phat_store_mvc_java_311.dao.implementations;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.*;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Status;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Profile;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.klozevitz.phat_store_mvc_java_311.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final OrderRepository repo;
    private final StockPositionService stockPositionService;
    private final ApplicationUserService applicationUserService;
    private final OrderPositionService orderPositionService;
    private final ProfileService profileService;
    private  final JavaMailSender mailSender;



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

    @Override
    public void pay(Order cart) {
        cart.setStatus(Status.IS_PAID);
        repo.save(cart);
        removeFromStock(cart);
        Profile currentProfile = cart.getProfile();
        currentProfile.getOrders().add(
                Order.builder()
                        .status(Status.CART)
                        .profile(currentProfile)
                        .positions(new HashSet<>())
                        .build()
        );
        profileService.save(currentProfile);
    }

    @Override
    public List<Order> ordersToDeliver() {
        return repo.ordersToDeliver();
    }

    private void addToCart(Order cart, OrderPosition positionToAdd) {
        // позиция на складе (товар) для дополнения корзины
        StockPosition stockPosition = positionToAdd.getStockPosition();
        // Если товар присутствует в корзине, изменяем его количество, в противном случае, добавляем новую строку в корзину
        if (isStockPositionPresent(cart, stockPosition)) {
            OrderPosition positionToIncrementAmount = cart.getPositions().stream()
                    .filter(orderPosition -> orderPosition.getStockPosition().getId() == stockPosition.getId())
                    .findFirst().get();
            if (stockPosition.getAmount() > positionToIncrementAmount.getAmount()) {
                positionToIncrementAmount.setAmount(positionToIncrementAmount.getAmount() + 1);
            }
        } else {
            if (stockPosition.getAmount() > 1) {
                // добавляем запись в чек (корзину)
                cart.getPositions().add(positionToAdd);
            }
        }
    }

    @Override
    public void deliver(int orderId) {
        Order orderToDeliver = repo.findById(orderId).get();
        orderToDeliver.setStatus(Status.IS_DELIVERED);
        repo.save(orderToDeliver);

        String subject = "Заказ оплачен";
        String messageBody = "Заказ будет доставлен по адресу: " + orderToDeliver.getProfile().getAddress();
        String emailTo = orderToDeliver.getProfile().getEmail();
        sendDeliveryEmail(subject, emailTo, messageBody);
    }

    private void sendDeliveryEmail(String subject, String emailTo, String messageBody) {
        SimpleMailMessage message = createMailMessage(subject, emailTo, messageBody);
        mailSender.send(message);
    }

    private SimpleMailMessage createMailMessage(String subject, String emailTo, String messageBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);
        mailMessage.setFrom(emailFrom);
        return mailMessage;
    }

    private boolean isStockPositionPresent(Order cart, StockPosition stockPosition) {
        return cart.getPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getStockPosition().getId() == stockPosition.getId());
    }

    private void removeFromStock(Order cart) {
        cart.getPositions().forEach(p -> stockPositionService.remove(p.getStockPosition(), p.getAmount()));
    }
}
