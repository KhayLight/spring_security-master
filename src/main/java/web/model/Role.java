package web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
@Setter
@Getter
@ToString
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "role")
    private String role;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role() {

    }

    @Override
    public String getAuthority() {
        return role;
    }
}
