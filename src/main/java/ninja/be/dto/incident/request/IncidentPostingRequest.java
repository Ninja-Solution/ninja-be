package ninja.be.dto.incident.request;

import lombok.Builder;
import lombok.Data;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IncidentPostingRequest {
    @NotBlank(message = "유저 정보가 존재해야 합니다.")
    private User user;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotNull(message = "타입을 입력하세요.")
    private IncidentType type;
    @NotNull(message = "주소을 입력하세요.")
    private Location location;
    @NotNull(message = "좌표을 입력하세요.")
    private Coordinate coordinate;

    @Builder
    public IncidentPostingRequest(User user, String title, IncidentType type, Location location, Coordinate coordinate) {
        this.user = user;
        this.title = title;
        this.type = type;
        this.location = location;
        this.coordinate = coordinate;
    }
}