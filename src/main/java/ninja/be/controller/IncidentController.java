package ninja.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ninja.be.dto.incident.request.IncidentPostingRequest;
import ninja.be.dto.incident.response.IncidentResponse;
import ninja.be.service.IncidentService;
import ninja.be.service.SseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/incident")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "incident", description = "사건 관련")
public class IncidentController {
    private final IncidentService incidentService;
    private final SseService sseService;

    @Operation(summary = "사건 등록", description = "사건 등록 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    public ResponseEntity<Long> createIncident(@AuthenticationPrincipal final String userId, @Valid @RequestBody final IncidentPostingRequest incidentPostingRequest) {
        Long incidentId = incidentService.saveIfNotDuplicate(Long.valueOf(userId), incidentPostingRequest);

        return ResponseEntity.ok(incidentId);
    }

    @Operation(summary = "사건 조회", description = "자신의 주소의 사건 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = IncidentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    public ResponseEntity<Page<IncidentResponse>> getIncidentByLocation(@AuthenticationPrincipal final String userId, @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<IncidentResponse> incidentResponses = incidentService.getIncidentByLocation(Long.valueOf(userId), pageable);
        return ResponseEntity.ok(incidentResponses);
    }

    @Operation(summary = "사건 sse 구독")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구독 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping(value = "/sse/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeIncident(@AuthenticationPrincipal final String userId,
                                        @RequestParam String city,
                                        @RequestParam String district,
                                        @RequestParam String ward) {
        String key = (city + district + ward).replace(" ", "");
        return sseService.subscribe(key);
    }
}
