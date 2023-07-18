package ninja.be.entity.embeddables;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Coordinate {
    private double x;
    private double y;
}
