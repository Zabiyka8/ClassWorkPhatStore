package org.klozevitz.phat_store_mvc_java_311.model.secuirty;

import jakarta.persistence.*;
import lombok.*;
import org.klozevitz.phat_store_mvc_java_311.model.BaseEntity;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Order;
import org.klozevitz.phat_store_mvc_java_311.model.entities.shop.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;

import static org.klozevitz.phat_store_mvc_java_311.model.secuirty.Role.ROLE_USER;

@Getter
@Setter
@Builder
@Entity
@Table(name = "application_user_t")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Profile profile;

    public ApplicationUser(String email, String password) {
        this.username = "";
        this.password = password;
        this.role = ROLE_USER;
        this.profile = Profile.builder()
                .user(this)
                .email(email)
                .orders(new HashSet<>() {{
                    add(Order.builder()
                            .isPaid(false)
                            .positions(new HashSet<>())
                            .build());
                }})
                .build();
    }

    public UserDetails securityUserFromEntity() {
        return new User(
                this.profile.getEmail(),
                password,
                true,
                true,
                true,
                true,
                new ArrayList<>(){{add(role);}}
        );
    }
}
