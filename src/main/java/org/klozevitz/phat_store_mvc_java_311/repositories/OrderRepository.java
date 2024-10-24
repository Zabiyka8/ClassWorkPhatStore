package org.klozevitz.phat_store_mvc_java_311.repositories;

import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * FROM order_t WHERE profile_id = ?1 AND status = 'CART' LIMIT 1;", nativeQuery = true)
    Order findCartByUserId(Integer profileId);
    @Query(value = "SELECT * FROM order_t WHERE status = 'IS_PAID';", nativeQuery = true)
    List<Order> ordersToDeliver();
}
