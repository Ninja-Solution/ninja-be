package ninja.be.dto.incident.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class IncidentPostingRequest {
    private String title;
    @NotNull(message = "타입을 입력하세요.")
    private IncidentType type;
    @NotNull(message = "주소을 입력하세요.")
    private String address;
    private Coordinate coordinate;

    @Builder
    public IncidentPostingRequest(String title, IncidentType type, String address, Coordinate coordinate) {
        this.title = title;
        this.type = type;
        this.address = address;
        this.coordinate = coordinate;
    }
}
