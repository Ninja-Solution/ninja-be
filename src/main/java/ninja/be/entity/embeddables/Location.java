package ninja.be.entity.embeddables;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Location {
    private String city;
    private String district;
    private String ward;
}
