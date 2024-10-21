package org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities;


import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.phat_store_mvc_java_311.model.BaseEntity;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Sex;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "item_t")
@AllArgsConstructor
public class Item extends BaseEntity {
    @Column(name = "article", unique = true)
    private String article;
    @Column(name = "model")
    private String model;
    @Column(name = "price")
    private Double price;
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Set<StockPosition> positions;

    public Item() {
        positions = new HashSet<>();
    }

    public boolean isInStock(){
        return !positions.stream()
                .filter(position -> position.getAmount()>0)
                .findFirst()
                .isEmpty();
    }
}
