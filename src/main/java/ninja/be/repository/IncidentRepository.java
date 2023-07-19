package ninja.be.repository;

import ninja.be.entity.Incident;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.entity.enums.IncidentType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Incident> findByLocation(Location location);

    @Query("SELECT i FROM Incident i WHERE i.createdDate >= :threeDaysAgo AND i.type = :type " +
            "AND i.coordinate.x BETWEEN :minX AND :maxX AND i.coordinate.y BETWEEN :minY AND :maxY")
    List<Incident> findIncidentsByDateTypeAndCoordinateRange(@Param("type") IncidentType type,
                                                             @Param("threeDaysAgo") LocalDateTime threeDaysAgo,
                                                             @Param("minX") double minX, @Param("minY") double minY,
                                                             @Param("maxX") double maxX, @Param("maxY") double maxY);
}
