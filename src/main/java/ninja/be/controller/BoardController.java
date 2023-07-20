package ninja.be.controller;

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

    @GetMapping("/all")
    public ResponseEntity<Page<BoardForm>> allBoard(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.findAll(pageable).map(board -> BoardForm.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .createdBy(board.getCreatedBy())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build()));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody BoardForm boardForm) {
        boardService.save(boardForm);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardForm> getBoard(@PathVariable("id") long id) {
        Board board = boardService.findById(id);
        if (board == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(BoardForm.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .createdBy(board.getCreatedBy())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable("id") long id,
                                             @AuthenticationPrincipal String userId,
                                             @RequestBody BoardForm boardForm) {
        if (!boardService.update(id, userId, boardForm)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<String> updateBoard(@PathVariable("id") long id, @RequestBody CommentForm comment) {
        if (!boardService.addComment(id, comment)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable("id") long id, @AuthenticationPrincipal String userId) {
        if (!boardService.deleteById(id, userId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
