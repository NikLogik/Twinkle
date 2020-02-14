package ru.nachos.db.repository.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nachos.db.entities.fire.ForestFuelType;

public interface ForestFuelTypeRepository extends JpaRepository<ForestFuelType, Long> {
    ForestFuelType getForestFuelTypeByTypeId(int typeId);
}
