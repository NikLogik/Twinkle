package git.niklogik.db.repository.fire;

import git.niklogik.db.entities.fire.FireIterationDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FireIterationRepository extends JpaRepository<FireIterationDAO, Long> {
    FireIterationDAO findFireIterationDAOByFireId_IdAndIterNumber(long fireId, int iterNumber);
    @Transactional
    void deleteAllByFireId_Id(long fireId);
    @Transactional
    @Query(value = "select iter from FireIterationDAO as iter where iter.fireId.id = ?1 and iter.iterNumber = (select  min(iterNumber) from FireIterationDAO )")
    FireIterationDAO findFirstIterByFireId(long fireId);
}
