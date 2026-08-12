package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.MatchDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchDayRepository extends JpaRepository<MatchDay, Long> {

    Optional<MatchDay> findByCompetitionAndNumber(Competition competition, String number);

}
