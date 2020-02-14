package ru.nachos.db.repository.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nachos.db.entities.fire.FireInfoDAO;

public interface FireInfoRepository extends JpaRepository<FireInfoDAO, Long> {
}
