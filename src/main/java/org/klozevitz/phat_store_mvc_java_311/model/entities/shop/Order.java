package org.klozevitz.phat_store_mvc_java_311.model.entities.shop;

import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.phat_store_mvc_java_311.model.BaseEntity;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_t")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Column(name = "isPaid")
    private Boolean isPaid;

    @ManyToOne
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Set<OrderPosition> positions;

    public double getPrice() {
        return positions.stream()
                .reduce(0.0,
                        (acc, cur) -> acc = acc + cur.getAmount() * cur.getStockPosition().getItem().getPrice(),
                        Double::sum
                );
    }
}
