package ninja.be.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardForm{
    private long id;
    private String title;
    private String content;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public BoardForm(long id, String title, String content, String createdBy, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}