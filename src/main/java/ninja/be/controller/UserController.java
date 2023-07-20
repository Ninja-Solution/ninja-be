package ninja.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ninja.be.dto.incident.request.IncidentPostingRequest;
import ninja.be.dto.user.response.UserResponse;
import ninja.be.entity.User;
import ninja.be.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "user", description = "USER 관련")
public class UserController {
    private final UserService userService;


    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponse> findByUserID(@AuthenticationPrincipal final String userId) {
        UserResponse user = userService.findByUserId(Long.valueOf(userId));
        return ResponseEntity.ok(user);
    }

}
