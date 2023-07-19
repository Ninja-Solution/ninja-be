package ninja.be.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ninja.be.entity.base.BaseTimeEntity;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Incident extends BaseTimeEntity {
    @Id
    @Column(name = "incident_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Enumerated(EnumType.STRING)
    private IncidentType type;

    @Embedded
    private Location location;

    private int incidentCount;

    @Embedded
    private Coordinate coordinate;

    @Builder
    public Incident(Long id, User user, String title, IncidentType type, Location location, Coordinate coordinate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.type = type;
        this.location = location;
        this.incidentCount = 1;
        this.coordinate = coordinate;
    }

    public void incidentCountIncrease() {
        incidentCount++;
    }
}
