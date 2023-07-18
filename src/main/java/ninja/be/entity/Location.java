package ninja.be.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Location {
    private String city;
    private String country;
    private String state;
}
