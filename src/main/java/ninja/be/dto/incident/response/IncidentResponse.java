package ninja.be.dto.incident.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ninja.be.dto.user.response.UserResponse;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.base.BaseTimeEntity;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;

import java.time.LocalDateTime;
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
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    public IncidentResponse(Long id, UserResponse userResponse, String title, IncidentType type, Location location, int incidentCount, Coordinate coordinate, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userResponse = userResponse;
        this.title = title;
        this.type = type;
        this.location = location;
        this.incidentCount = incidentCount;
        this.coordinate = coordinate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static IncidentResponse from(Incident entity) {
        return IncidentResponse.builder()
                .id(entity.getId())
                .userResponse(UserResponse.from(entity.getUser()))
                .title(entity.getTitle())
                .type(entity.getType())
                .location(entity.getLocation())
                .incidentCount(entity.getIncidentCount())
                .coordinate(entity.getCoordinate())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getLastModifiedDate())
                .build();
    }

    public static List<IncidentResponse> from(List<Incident> entity) {
        return entity.stream()
                .map(IncidentResponse::from)
                .collect(Collectors.toList());
    }
}
