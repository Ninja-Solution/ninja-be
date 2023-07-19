package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.dto.incident.request.IncidentPostingRequest;
import ninja.be.dto.incident.response.IncidentResponse;
import ninja.be.entity.Incident;
import ninja.be.entity.User;
import ninja.be.entity.embeddables.Coordinate;
import ninja.be.entity.enums.IncidentType;
import ninja.be.exception.user.UserNotFoundException;
import ninja.be.repository.IncidentRepository;
import ninja.be.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    public Long createIncident(Long userId, IncidentPostingRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Incident incident = Incident.builder()
                .user(user)
                .title(request.getTitle())
                .type(request.getType())
                .location(request.getLocation())
                .coordinate(request.getCoordinate())
                .build();

        incidentRepository.save(incident);

        return incident.getId();
    }

    public Long saveIfNotDuplicate(Long userId, IncidentPostingRequest request) {
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
            return createIncident(userId, request);
        }
    }

    public List<IncidentResponse> getIncidentByLocation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Incident> incidents = incidentRepository.findByLocation(user.getLocation());

        return incidents.stream()
                .map(IncidentResponse::from)
                .collect(Collectors.toList());
    }
}
