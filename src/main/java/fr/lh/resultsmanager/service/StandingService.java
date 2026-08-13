package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.response.StandingResponseDto;
import fr.lh.resultsmanager.dtos.response.TeamSummaryDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.model.Status;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import fr.lh.resultsmanager.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StandingService {

    private final CompetitionRepository competitionRepository;
    private final MatchDayRepository matchDayRepository;
    private final MatchRepository matchRepository;

    public List<StandingResponseDto> getStandings(
            Long competitionId,
            String matchDayLabel) {

        Competition competition = competitionRepository
                .findById(competitionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Competition not found with id: "
                                        + competitionId));

        MatchDay matchDay = matchDayRepository
                .findByCompetitionAndLabel(
                        competition,
                        matchDayLabel)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MatchDay not found: "
                                        + matchDayLabel));

        List<Match> matches =
                matchRepository.findFinishedMatchesUntilMatchDay(
                        competitionId,
                        matchDay.getPosition(),
                        Status.FINISHED);

        return calculateStandings(matches);
    }

    private List<StandingResponseDto> calculateStandings(
            List<Match> matches) {

        Map<Long, StandingStats> standings = new HashMap<>();

        for (Match match : matches) {

            Team homeTeam = match.getHomeTeam();
            Team awayTeam = match.getAwayTeam();

            StandingStats homeStats =
                    standings.computeIfAbsent(
                            homeTeam.getId(),
                            id -> new StandingStats(homeTeam));

            StandingStats awayStats =
                    standings.computeIfAbsent(
                            awayTeam.getId(),
                            id -> new StandingStats(awayTeam));

            homeStats.addMatch(
                    match.getHomeScore(),
                    match.getAwayScore());

            awayStats.addMatch(
                    match.getAwayScore(),
                    match.getHomeScore());
        }

        return standings.values().stream()
                .sorted(StandingStats::compareTo)
                .map(StandingStats::toDto)
                .toList();
    }

    private static class StandingStats {

        private final Team team;

        private int played;
        private int wins;
        private int draws;
        private int losses;
        private int goalsFor;
        private int goalsAgainst;
        private int points;

        private StandingStats(Team team) {
            this.team = team;
        }

        private void addMatch(
                Integer scored,
                Integer conceded) {

            played++;
            goalsFor += scored;
            goalsAgainst += conceded;

            if (scored > conceded) {
                wins++;
                points += 3;
            } else if (scored.equals(conceded)) {
                draws++;
                points++;
            } else {
                losses++;
            }
        }

        private int getGoalDifference() {
            return goalsFor - goalsAgainst;
        }

        private static int compareTo(
                StandingStats first,
                StandingStats second) {

            int pointsComparison =
                    Integer.compare(second.points, first.points);

            if (pointsComparison != 0) {
                return pointsComparison;
            }

            int goalDifferenceComparison =
                    Integer.compare(
                            second.getGoalDifference(),
                            first.getGoalDifference());

            if (goalDifferenceComparison != 0) {
                return goalDifferenceComparison;
            }

            return Integer.compare(
                    second.goalsFor,
                    first.goalsFor);
        }

        private StandingResponseDto toDto() {

            return new StandingResponseDto(
                    new TeamSummaryDto(
                            team.getId(),
                            team.getName(),
                            team.getShortName()),
                    played,
                    wins,
                    draws,
                    losses,
                    goalsFor,
                    goalsAgainst,
                    getGoalDifference(),
                    points
            );
        }
    }


}
