package ninja.be.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ninja.be.entity.base.BaseTimeEntity;
import ninja.be.entity.enums.IncidentType;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
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

    @ColumnDefault("1")
    private int incidentCount;

    @Builder
    public Incident(Long id, User user, String title, IncidentType type, Location location, int incidentCount) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.type = type;
        this.location = location;
        this.incidentCount = incidentCount;
    }

    public void incidentCountIncrease() {
        incidentCount++;
    }
}
