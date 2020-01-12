package ru.nachos.db.repository.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.nachos.db.model.fire.FireFrontModel;

public interface FireFrontModelRepository extends JpaRepository<FireFrontModel, Long> {

    FireFrontModel findFireFrontModelByFire_FireIdAndIterNumber(long fire_id, int iter_number);
    @Transactional
    void deleteAllByFire_FireId(long fireId);
    @Transactional
    @Query(value = "select model from FireFrontModel as model where model.fire.fireId = ?1 and model.iterNumber = (select min(iterNumber) from FireFrontModel)")
    FireFrontModel findFireFrontModelByFire_FireIdWithMinIterNumber(long fireId);
}
