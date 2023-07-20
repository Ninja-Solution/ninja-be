package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.dto.incident.request.IncidentPostingRequest;
import ninja.be.dto.incident.response.IncidentResponse;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.embeddables.Location;
import ninja.be.exception.user.UserNotFoundException;
import ninja.be.repository.IncidentRepository;
import ninja.be.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final SseService sseService;

    public Long createIncident(Long userId, IncidentPostingRequest request, Location location, String title) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Incident incident = Incident.builder()
                .user(user)
                .title(title)
                .type(request.getType())
                .location(location)
                .coordinate(request.getCoordinate())
                .build();

        incidentRepository.save(incident);

        IncidentResponse incidentResponse = IncidentResponse.from(incident);

        sseService.notify(incidentResponse);

        return incidentResponse.getId();
    }

    public Long saveIfNotDuplicate(Long userId, IncidentPostingRequest request) {
        Location location = getLocation(request);

        String title = getTitle(request, location);
        Coordinate minXy = new Coordinate(request.getCoordinate().getX() - 0.00003, request.getCoordinate().getY() - 0.00003);
        Coordinate maxXy = new Coordinate(request.getCoordinate().getX() + 0.00003, request.getCoordinate().getY() + 0.00003);

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        List<Incident> existingIncidents = incidentRepository.findIncidentsByDateTypeAndCoordinateRange(
                request.getType(), threeDaysAgo, minXy.getX(), minXy.getY(), maxXy.getX(), maxXy.getY());

        if (existingIncidents.size() > 0) {
            for (Incident existingIncident : existingIncidents) {
                existingIncident.incidentCountIncrease();
                incidentRepository.save(existingIncident);
            }
            throw new RuntimeException("이미 중복된 사건입니다.");
        } else {
            return createIncident(userId, request, location, title);
        }
    }

    private String getTitle(IncidentPostingRequest request, Location location) {
        return location.getCity() + " " + location.getDistrict() + "에서 [" + request.getType().getIncident() + "] 발생";
    }

    private Location getLocation(IncidentPostingRequest request) {
        String address = request.getAddress();
        String[] s = address.split(" ");
        return new Location(s[1], s[2]);
    }

    public Page<IncidentResponse> getIncidentByLocation(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        Page<Incident> incidents = incidentRepository.findByLocationAndCreatedDateAfter(user.getLocation(), threeDaysAgo, pageable);

        return incidents.map(IncidentResponse::from);
    }

    public Page<IncidentResponse> findAllPaging(Pageable pageable) {
        Page<Incident> all = incidentRepository.findAll(pageable);

        return all.map(IncidentResponse::from);
    }
}
