package ninja.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ninja.be.dto.board.BoardForm;
import ninja.be.dto.comment.CommentForm;
import ninja.be.entity.Board;
import ninja.be.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "board", description = "BOARD 관련")
public class BoardController {
    private final BoardService boardService;
    @Operation(summary = "모든 게시판 조회", description = "모든 게시판 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<Page<BoardForm>> allBoard(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.findAll(pageable).map(board -> BoardForm.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdBy(board.getCreatedBy())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build()));
    }

    @Operation(summary = "게시판 생성", description = "게시판 생성 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 생성 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody BoardForm boardForm) {
        boardService.save(boardForm);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "게시판 조회", description = "게시판 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BoardForm> getBoard(@PathVariable("id") long id) {
        Board board = boardService.findById(id);
        if (board == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(BoardForm.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdBy(board.getCreatedBy())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build());
    }

    @Operation(summary = "게시판 변경", description = "게시판 변경 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable("id") long id,
                                             @AuthenticationPrincipal String userId,
                                             @RequestBody BoardForm boardForm) {
        if (!boardService.update(id, userId, boardForm)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 변경", description = "댓글 변경 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}/comment")
    public ResponseEntity<String> updateBoard(@PathVariable("id") long id, @RequestBody CommentForm comment) {
        if (!boardService.addComment(id, comment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시판 삭제", description = "게시판 삭제 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable("id") long id, @AuthenticationPrincipal String userId) {
        if (!boardService.deleteById(id, userId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
