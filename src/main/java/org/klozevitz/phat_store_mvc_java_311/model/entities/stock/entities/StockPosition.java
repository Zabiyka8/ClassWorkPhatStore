package org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities;

import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.phat_store_mvc_java_311.model.BaseEntity;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Color;

@Getter
@Setter
@Builder
@Entity
@Table(name = "stock_position_t")
@AllArgsConstructor
@NoArgsConstructor
public class StockPosition extends BaseEntity {
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;
    @Column(name = "size")
    private String size;
    @ManyToOne
    private Item item;
}
