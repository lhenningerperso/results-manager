package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {
}
