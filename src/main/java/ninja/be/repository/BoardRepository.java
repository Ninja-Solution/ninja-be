package ninja.be.repository;

import ninja.be.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findById(long id);

    @Query("select b from Board b left join fetch b.comment where b.id = :id")
    Board findCommentById(@Param("id") long id);
    void deleteById(long id);
    Page<Board> findAll(Pageable pageable);
}
