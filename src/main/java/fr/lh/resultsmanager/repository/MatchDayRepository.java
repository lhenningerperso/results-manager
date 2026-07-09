package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.MatchDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDayRepository extends JpaRepository<MatchDay, Long> {}
