package git.niklogik.db.repository.fire;

import git.niklogik.db.entities.fire.ForestFuelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForestFuelTypeRepository extends JpaRepository<ForestFuelType, Long> {
    Optional<ForestFuelType> findByTypeId(Integer typeId);
}
