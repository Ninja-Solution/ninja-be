package ninja.be.dto.incident.response;

import lombok.Builder;
import lombok.Data;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;

@Data
public class IncidentResponse {
    private Long id;
    private User user;
    private String title;
    private IncidentType type;
    private Location location;
    private int incidentCount;
    private Coordinate coordinate;

    @Builder
    public IncidentResponse(Long id, User user, String title, IncidentType type, Location location, int incidentCount, Coordinate coordinate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.type = type;
        this.location = location;
        this.incidentCount = incidentCount;
        this.coordinate = coordinate;
    }

    public static IncidentResponse from(Incident entity) {
        return IncidentResponse.builder()
                .id(entity.getId())
                .user(entity.getUser())
                .title(entity.getTitle())
                .type(entity.getType())
                .location(entity.getLocation())
                .incidentCount(entity.getIncidentCount())
                .coordinate(entity.getCoordinate()).build();
    }
}
