package org.klozevitz.phat_store_mvc_java_311.model.entities.shop;

import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.phat_store_mvc_java_311.model.BaseEntity;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_position_t")
@AllArgsConstructor
@NoArgsConstructor
public class OrderPosition extends BaseEntity {
    @Column(name = "amount")
    private Integer amount;
    @ManyToOne
    private Order order;
    @ManyToOne
    @JoinColumn(name = "stock_pos_id")
    private StockPosition stockPosition;
}