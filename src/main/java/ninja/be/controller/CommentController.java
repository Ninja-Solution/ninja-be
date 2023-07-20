package ninja.be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ninja.be.dto.comment.CommentForm;
import ninja.be.entity.Comment;
import ninja.be.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "comment", description = "COMMENT 관련")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Page<CommentForm>> getComment(@PathVariable("id") long id,
                                                    @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(commentService.findAllById(id, pageable).map(comment -> CommentForm.builder()
                .content(comment.getContent())
                .createdBy(comment.getCreatedBy())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable("id") long id,
                                                 @RequestBody CommentForm comment,
                                                 @AuthenticationPrincipal String userId) {
        if (!commentService.update(id, comment.getContent(), userId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") long id, @AuthenticationPrincipal String userId) {
        if (!commentService.deleteById(id, userId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
