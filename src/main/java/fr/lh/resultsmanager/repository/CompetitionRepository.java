package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Optional<Competition> findByLeagueAndSeason(League league, String season);
}
