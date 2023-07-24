package ninja.be.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ninja.be.dto.comment.CommentForm;
import ninja.be.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private long id;
    private String title;
    private String content;
    private Long creater;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comment;

    @Builder
    public Board(long id, String title, String content, Long creater, List<Comment> comment) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creater = creater;
        this.comment = comment;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        this.comment.add(comment);
        comment.addBoard(this);
    }
}
