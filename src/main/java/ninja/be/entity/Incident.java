package ninja.be.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
    private int reports;
}
