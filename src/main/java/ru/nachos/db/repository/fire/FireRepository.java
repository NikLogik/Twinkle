package ru.nachos.db.repository.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.nachos.db.entities.fire.FireDAO;

public interface FireRepository extends JpaRepository<FireDAO, Long> {
    @Transactional
    void deleteById(long fireId);
}
