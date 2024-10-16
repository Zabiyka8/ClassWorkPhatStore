package org.klozevitz.phat_store_mvc_java_311.model.entities.shop;

import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.phat_store_mvc_java_311.model.BaseEntity;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "profile_t")
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Set<Order> orders;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private ApplicationUser user;

    public Profile(String email, ApplicationUser user) {
        this.name = "";
        this.email = email;
        this.user = user;
        this.orders = new HashSet<>();
    }
}
