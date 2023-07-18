package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.repository.IncidentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final IncidentRepository incidentRepository;

}
