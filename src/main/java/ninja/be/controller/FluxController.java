package ninja.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ninja.be.service.FluxService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/flux")
@RequiredArgsConstructor
public class FluxController {
    private final FluxService fluxService;

    @Operation(summary = "사건 flux 구독")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구독 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping(value = "/incident/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> subscribeIncident(@AuthenticationPrincipal final String userId) {
        return fluxService.subscribe(userId);
    }
}
