package ninja.be.dto.user.response;

import lombok.Builder;
import lombok.Data;
import ninja.be.dto.incident.response.IncidentResponse;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Location;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private Location location;

    @Builder
    public UserResponse(Long id, String email, String username, Location location) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.location = location;
    }

    public static UserResponse from(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getLocation()
        );
    }
}
