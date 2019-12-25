package ru.nachos.db.repository.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.nachos.db.model.fire.FireModel;

public interface FireModelRepository extends JpaRepository<FireModel, Long> {
    @Transactional
    void deleteByFireId(long fireId);
}
