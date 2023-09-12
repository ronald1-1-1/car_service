package task.aisa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import task.aisa.user.UserRole;

import java.util.Set;

@Entity
@Table(name = "user_tb")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL)
    private Set<OrderEntity> orders;
}
