package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.entity.Comment;
import ninja.be.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Page<Comment> findAllById(long id, Pageable pageable) {
        return commentRepository.findAllById(id, pageable);
    }

    public boolean deleteById(long id, String deleteUser) {
        Comment comment = commentRepository.findById(id);
        if (comment == null || comment.getCreatedBy().equals(deleteUser)) {
            return false;
        }
        commentRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean update(long id, String content, String modifyUser) {
        Comment comment = commentRepository.findById(id);
        if (comment == null || comment.getCreatedBy().equals(modifyUser)) {
            return false;
        }
        comment.updateComment(content);
        return true;
    }
}
