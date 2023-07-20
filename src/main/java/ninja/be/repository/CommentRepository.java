package ninja.be.repository;

import ninja.be.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findById(long id);
    void deleteById(long id);
    @Query("select c from Comment c where c.board.id = ?1")
    Page<Comment> findAllById(long id, Pageable pageable);
}
