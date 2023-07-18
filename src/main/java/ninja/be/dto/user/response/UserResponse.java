package ninja.be.dto.user.response;

import lombok.Builder;
import lombok.Data;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Location;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String username;
    private Location location;
    private List<Incident> incidents;

    @Builder
    public UserResponse(Long id, String email, String password, String username, Location location, List<Incident> incidents) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.location = location;
        this.incidents = incidents;
    }

    public static UserResponse from(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getUsername(),
                entity.getLocation(),
                entity.getIncidents()
        );
    }
}
