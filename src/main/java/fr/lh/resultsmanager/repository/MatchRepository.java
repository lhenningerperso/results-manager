package fr.lh.resultsmanager.repository;

import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByExternalId(Long externalId);

    @Query("""
            SELECT m
            FROM Match m
            JOIN m.matchDay md
            WHERE md.competition.id = :competitionId
            AND md.position <= :matchDayPosition
            AND m.status = :status
            """)
    List<Match> findFinishedMatchesUntilMatchDay(
            Long competitionId,
            int matchDayPosition,
            Status status
    );
}
