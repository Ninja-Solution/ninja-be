package ninja.be.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentForm {
    private long id;
    private String content;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public CommentForm(long id, String content, String createdBy, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
