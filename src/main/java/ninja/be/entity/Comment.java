package ninja.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ninja.be.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @Builder
    public Comment(long id, String content, Board board) {
        this.id = id;
        this.content = content;
        this.board = board;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void addBoard(Board board) {
        this.board = board;
    }
}
