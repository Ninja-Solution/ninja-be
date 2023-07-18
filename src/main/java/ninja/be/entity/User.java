package ninja.be.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ninja.be.entity.base.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String username;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Incident> incidents;

    @Builder
    public User(Long id, String email, String password, String username, Location location, List<Incident> incidents) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.location = location;
        this.incidents = incidents;
    }
}
