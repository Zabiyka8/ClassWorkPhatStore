package org.klozevitz.phat_store_mvc_java_311.repositories;

import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Integer> {
    @Query(value = "SELECT * FROM order_position_t WHERE color = ?1 AND size =?2 AND item_id = ?3 LIMIT 1;", nativeQuery = true)
    OrderPosition filterOrderPositionByColorAndSizeAndItemId(String color, String size, Integer itemId);
}
