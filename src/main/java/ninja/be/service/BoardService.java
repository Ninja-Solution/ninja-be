package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.dto.board.BoardForm;
import ninja.be.dto.comment.CommentForm;
import ninja.be.entity.Board;
import ninja.be.entity.Comment;
import ninja.be.entity.User;
import ninja.be.repository.BoardRepository;
import ninja.be.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board findById(long id) {
        return boardRepository.findById(id);
    }

    public boolean deleteById(long id, String deleteUser) {
        Board board = boardRepository.findById(id);
        if (board == null || board.getCreater() != Long.parseLong(deleteUser)) {
            return false;
        }
        boardRepository.deleteById(id);
        return true;
    }

    public void save(BoardForm boardForm, long creater) {
        Board board = Board.builder()
                .title(boardForm.getTitle())
                .content(boardForm.getContent())
                .creater(creater)
                .build();
        boardRepository.save(board);
    }

    @Transactional
    public boolean update(long id, String modifyUser, BoardForm boardForm) {
        Board board = boardRepository.findById(id);
        if (board == null || board.getCreater() != Long.parseLong(modifyUser)) {
            return false;
        }
        board.updateBoard(boardForm.getTitle(), boardForm.getContent());
        return true;
    }

    @Transactional
    public boolean addComment(long id, CommentForm commentForm, long creater) {
        Board updateBoard = boardRepository.findCommentById(id);
        if (updateBoard == null) {
            return false;
        }
        updateBoard.addComment(Comment.builder()
                .content(commentForm.getContent())
                .creater(creater)
                .build());
        return true;
    }

}
