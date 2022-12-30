package git.niklogik.db.repository.fire;

import git.niklogik.db.entities.fire.FireInfoDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireInfoRepository extends JpaRepository<FireInfoDAO, Long> {
}
