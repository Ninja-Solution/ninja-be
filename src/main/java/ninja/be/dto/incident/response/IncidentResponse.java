package ninja.be.dto.incident.response;

import lombok.Builder;
import lombok.Data;
import ninja.be.dto.user.response.UserResponse;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class IncidentResponse {
    private Long id;
    private UserResponse userResponse;
    private String title;
    private IncidentType type;
    private Location location;
    private int incidentCount;
    private Coordinate coordinate;

    @Builder
    public IncidentResponse(Long id, UserResponse userResponse, String title, IncidentType type, Location location, int incidentCount, Coordinate coordinate) {
        this.id = id;
        this.userResponse = userResponse;
        this.title = title;
        this.type = type;
        this.location = location;
        this.incidentCount = incidentCount;
        this.coordinate = coordinate;
    }

    public static IncidentResponse from(Incident entity) {
        return IncidentResponse.builder()
                .id(entity.getId())
                .userResponse(UserResponse.from(entity.getUser()))
                .title(entity.getTitle())
                .type(entity.getType())
                .location(entity.getLocation())
                .incidentCount(entity.getIncidentCount())
                .coordinate(entity.getCoordinate()).build();
    }

    public static List<IncidentResponse> from(List<Incident> entity) {
        return entity.stream()
                .map(IncidentResponse::from)
                .collect(Collectors.toList());
    }
}
