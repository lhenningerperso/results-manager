package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaguesRepository extends JpaRepository<League, Long> {
}
