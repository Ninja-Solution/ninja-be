package ninja.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ninja.be.entity.base.BaseTimeEntity;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.Authority;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String username;

    @Embedded
    private Location location;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Incident> incidents;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public User(Long id, String email, String password, String username, Location location, List<Incident> incidents, Authority authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.location = location;
        this.incidents = incidents;
        this.authority = authority;
    }

    public void updateUser(String password, String username, Location location){

    }
}
