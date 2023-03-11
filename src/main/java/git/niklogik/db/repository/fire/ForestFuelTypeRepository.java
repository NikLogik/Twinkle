package git.niklogik.db.repository.fire;

import git.niklogik.db.entities.fire.ForestFuelTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForestFuelTypeRepository extends JpaRepository<ForestFuelTypeDao, Long> {
    Optional<ForestFuelTypeDao> findByTypeId(Integer typeId);
}
