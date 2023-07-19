package ninja.be.entity.enums;

public enum IncidentType {
    LANDSLIDE("가뭄"),
    FLOOD("홍수"),
    INUNDATION("침수"),
    WILDFIRE("산불"),
    FIRE("화재"),
    EARTHQUAKE("지진"),
    ;

    private final String incident;
    IncidentType(String incident) {
        this.incident=incident;
    }
    public String getIncident(){
        return this.incident;
    }
}