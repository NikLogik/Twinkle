package ru.nachos.db.repository.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.nachos.db.model.fire.FireFrontModel;

public interface FireFrontModelRepository extends JpaRepository<FireFrontModel, Long> {

    FireFrontModel findFireFrontModelByFire_FireIdAndIterNumber(long fire_id, int iter_number);
    @Transactional
    void deleteAllByFire_FireId(long fireId);

}
