package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByExternalId(Long externalId);

}
