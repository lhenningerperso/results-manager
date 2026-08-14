package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.response.StandingResponseDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.model.Status;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import fr.lh.resultsmanager.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandingServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private MatchDayRepository matchDayRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private StandingService standingService;

    private Competition competition;
    private MatchDay matchDay;

    private Team teamA;
    private Team teamB;
    private Team teamC;
    private Team teamD;

    @BeforeEach
    void setUp() {

        teamA = Team.builder()
                .externalId(1L)
                .name("Team A")
                .shortName("A")
                .city("City A")
                .build();
        teamA.setId(1L);

        teamB = Team.builder()
                .externalId(2L)
                .name("Team B")
                .shortName("B")
                .city("City B")
                .build();
        teamB.setId(2L);

        teamC = Team.builder()
                .externalId(3L)
                .name("Team C")
                .shortName("C")
                .city("City C")
                .build();
        teamC.setId(3L);

        teamD = Team.builder()
                .externalId(4L)
                .name("Team D")
                .shortName("D")
                .city("City D")
                .build();
        teamD.setId(4L);

        competition = Competition.builder()
                .season("2024")
                .build();
        competition.setId(1L);

        matchDay = MatchDay.builder()
                .competition(competition)
                .label("J1")
                .position(1)
                .build();
        matchDay.setId(1L);
    }


    @Test
    void shouldCalculateStandingsAfterFirstMatchDay() {

        // J1 results:
        // Team A 2 - 0 Team B
        // Team C 1 - 1 Team D
        Match match1 = Match.builder()
                .externalId(101L)
                .matchDay(matchDay)
                .homeTeam(teamA)
                .awayTeam(teamB)
                .homeScore(2)
                .awayScore(0)
                .status(Status.FINISHED)
                .build();

        Match match2 = Match.builder()
                .externalId(102L)
                .matchDay(matchDay)
                .homeTeam(teamC)
                .awayTeam(teamD)
                .homeScore(1)
                .awayScore(1)
                .status(Status.FINISHED)
                .build();

        when(competitionRepository.findById(1L))
                .thenReturn(Optional.of(competition));

        when(matchDayRepository.findByCompetitionAndLabel(
                competition,
                "J1"))
                .thenReturn(Optional.of(matchDay));

        when(matchRepository.findFinishedMatchesUntilMatchDay(
                1L,
                1,
                Status.FINISHED))
                .thenReturn(List.of(match1, match2));

        // Team A won 2-0, so it should be ranked first:
        // 1 game played, 1 win, 3 points and +2 goal difference.
        List<StandingResponseDto> standings =
                standingService.getStandings(1L, "J1");

        assertThat(standings).hasSize(4);

        StandingResponseDto first = standings.get(0);

        assertThat(first.team().id()).isEqualTo(1L);
        assertThat(first.team().name()).isEqualTo("Team A");
        assertThat(first.played()).isEqualTo(1);
        assertThat(first.wins()).isEqualTo(1);
        assertThat(first.draws()).isZero();
        assertThat(first.losses()).isZero();
        assertThat(first.goalsFor()).isEqualTo(2);
        assertThat(first.goalsAgainst()).isZero();
        assertThat(first.goalDifference()).isEqualTo(2);
        assertThat(first.points()).isEqualTo(3);

        // Team C drew 1-1, so it should have one point.
        StandingResponseDto second = standings.get(1);

        assertThat(second.team().id()).isEqualTo(3L);
        assertThat(second.played()).isEqualTo(1);
        assertThat(second.wins()).isZero();
        assertThat(second.draws()).isEqualTo(1);
        assertThat(second.losses()).isZero();
        assertThat(second.goalsFor()).isEqualTo(1);
        assertThat(second.goalsAgainst()).isEqualTo(1);
        assertThat(second.goalDifference()).isZero();
        assertThat(second.points()).isEqualTo(1);
    }

    @Test
    void shouldCalculateCumulativeStandingsAfterSecondMatchDay() {

        // J1 results:
        // Team A 2 - 0 Team B
        // Team C 1 - 1 Team D
        Match match1 = Match.builder()
                .externalId(101L)
                .matchDay(matchDay)
                .homeTeam(teamA)
                .awayTeam(teamB)
                .homeScore(2)
                .awayScore(0)
                .status(Status.FINISHED)
                .build();

        Match match2 = Match.builder()
                .externalId(102L)
                .matchDay(matchDay)
                .homeTeam(teamC)
                .awayTeam(teamD)
                .homeScore(1)
                .awayScore(1)
                .status(Status.FINISHED)
                .build();

        // Create the second matchday.
        MatchDay secondMatchDay = MatchDay.builder()
                .competition(competition)
                .label("J2")
                .position(2)
                .build();
        secondMatchDay.setId(2L);

        // J2 results:
        // Team A 0 - 1 Team C
        // Team B 2 - 0 Team D
        Match match3 = Match.builder()
                .externalId(103L)
                .matchDay(secondMatchDay)
                .homeTeam(teamA)
                .awayTeam(teamC)
                .homeScore(0)
                .awayScore(1)
                .status(Status.FINISHED)
                .build();

        Match match4 = Match.builder()
                .externalId(104L)
                .matchDay(secondMatchDay)
                .homeTeam(teamB)
                .awayTeam(teamD)
                .homeScore(2)
                .awayScore(0)
                .status(Status.FINISHED)
                .build();

        when(competitionRepository.findById(1L))
                .thenReturn(Optional.of(competition));

        when(matchDayRepository.findByCompetitionAndLabel(
                competition,
                "J2"))
                .thenReturn(Optional.of(secondMatchDay));

        // The repository must return both J1 and J2 matches.
        when(matchRepository.findFinishedMatchesUntilMatchDay(
                1L,
                2,
                Status.FINISHED))
                .thenReturn(List.of(
                        match1,
                        match2,
                        match3,
                        match4
                ));

        List<StandingResponseDto> standings =
                standingService.getStandings(1L, "J2");

        assertThat(standings).hasSize(4);

        // Team C:
        // J1: draw (1 point)
        // J2: win  (3 points)
        // Total: 4 points
        StandingResponseDto teamCStanding = standings.stream()
                .filter(standing -> standing.team().id().equals(3L))
                .findFirst()
                .orElseThrow();

        assertThat(teamCStanding.played()).isEqualTo(2);
        assertThat(teamCStanding.wins()).isEqualTo(1);
        assertThat(teamCStanding.draws()).isEqualTo(1);
        assertThat(teamCStanding.losses()).isZero();
        assertThat(teamCStanding.goalsFor()).isEqualTo(2);
        assertThat(teamCStanding.goalsAgainst()).isEqualTo(1);
        assertThat(teamCStanding.goalDifference()).isEqualTo(1);
        assertThat(teamCStanding.points()).isEqualTo(4);

        // Team A:
        // J1: win  (3 points)
        // J2: loss (0 points)
        // Total: 3 points
        StandingResponseDto teamAStanding = standings.stream()
                .filter(standing -> standing.team().id().equals(1L))
                .findFirst()
                .orElseThrow();

        assertThat(teamAStanding.played()).isEqualTo(2);
        assertThat(teamAStanding.wins()).isEqualTo(1);
        assertThat(teamAStanding.draws()).isZero();
        assertThat(teamAStanding.losses()).isEqualTo(1);
        assertThat(teamAStanding.goalsFor()).isEqualTo(2);
        assertThat(teamAStanding.goalsAgainst()).isEqualTo(1);
        assertThat(teamAStanding.goalDifference()).isEqualTo(1);
        assertThat(teamAStanding.points()).isEqualTo(3);

        // Team B:
        // J1: loss (0 points)
        // J2: win  (3 points)
        // Total: 3 points
        StandingResponseDto teamBStanding = standings.stream()
                .filter(standing -> standing.team().id().equals(2L))
                .findFirst()
                .orElseThrow();

        assertThat(teamBStanding.played()).isEqualTo(2);
        assertThat(teamBStanding.wins()).isEqualTo(1);
        assertThat(teamBStanding.draws()).isZero();
        assertThat(teamBStanding.losses()).isEqualTo(1);
        assertThat(teamBStanding.goalsFor()).isEqualTo(2);
        assertThat(teamBStanding.goalsAgainst()).isEqualTo(2);
        assertThat(teamBStanding.goalDifference()).isZero();
        assertThat(teamBStanding.points()).isEqualTo(3);
    }

    @Test
    void shouldSortStandingsByPointsGoalDifferenceAndGoalsScored() {

        /*
         * Results:
         *
         * Team A 3 - 0 Team D
         * Team B 2 - 0 Team D
         * Team C 3 - 2 Team D
         *
         * Expected:
         *
         * Team A → 3 pts, +3 GD, 3 GF
         * Team B → 3 pts, +2 GD, 2 GF
         * Team C → 3 pts, +1 GD, 3 GF
         *
         * Team D → 0 pts, -6 GD, 2 GF
         *
         * The ranking must therefore be:
         * 1. Team A
         * 2. Team B
         * 3. Team C
         * 4. Team D
         *
         * This verifies that points are the primary ranking criterion,
         * followed by goal difference.
         */

        Match match1 = Match.builder()
                .externalId(101L)
                .matchDay(matchDay)
                .homeTeam(teamA)
                .awayTeam(teamD)
                .homeScore(3)
                .awayScore(0)
                .status(Status.FINISHED)
                .build();

        Match match2 = Match.builder()
                .externalId(102L)
                .matchDay(matchDay)
                .homeTeam(teamB)
                .awayTeam(teamD)
                .homeScore(2)
                .awayScore(0)
                .status(Status.FINISHED)
                .build();

        Match match3 = Match.builder()
                .externalId(103L)
                .matchDay(matchDay)
                .homeTeam(teamC)
                .awayTeam(teamD)
                .homeScore(3)
                .awayScore(2)
                .status(Status.FINISHED)
                .build();

        when(competitionRepository.findById(1L))
                .thenReturn(Optional.of(competition));

        when(matchDayRepository.findByCompetitionAndLabel(
                competition,
                "J1"))
                .thenReturn(Optional.of(matchDay));

        when(matchRepository.findFinishedMatchesUntilMatchDay(
                1L,
                1,
                Status.FINISHED))
                .thenReturn(List.of(match1, match2, match3));

        List<StandingResponseDto> standings =
                standingService.getStandings(1L, "J1");

        assertThat(standings).hasSize(4);

        // Team A has the best goal difference among teams
        // with three points and must therefore be ranked first.
        assertThat(standings.get(0).team().id()).isEqualTo(1L);
        assertThat(standings.get(0).points()).isEqualTo(3);
        assertThat(standings.get(0).goalDifference()).isEqualTo(3);

        // Team B has three points and a +2 goal difference,
        // so it must be ranked below Team A.
        assertThat(standings.get(1).team().id()).isEqualTo(2L);
        assertThat(standings.get(1).points()).isEqualTo(3);
        assertThat(standings.get(1).goalDifference()).isEqualTo(2);

        // Team C also has three points but the lowest goal difference
        // among the three winning teams.
        assertThat(standings.get(2).team().id()).isEqualTo(3L);
        assertThat(standings.get(2).points()).isEqualTo(3);
        assertThat(standings.get(2).goalDifference()).isEqualTo(1);

        // Team D has no points and must therefore be last.
        assertThat(standings.get(3).team().id()).isEqualTo(4L);
        assertThat(standings.get(3).points()).isZero();
    }

    @Test
    void shouldUseGoalsScoredWhenPointsAndGoalDifferenceAreEqual() {

        /*
         * Results:
         *
         * Team A 2 - 1 Team D
         * Team B 1 - 0 Team C
         *
         * Team A → 3 points, +1 goal difference, 2 goals scored
         * Team B → 3 points, +1 goal difference, 1 goal scored
         *
         * Since Team A and Team B have the same number of points
         * and the same goal difference, goals scored must determine
         * their ranking.
         *
         * Expected order:
         * Team A
         * Team B
         */

        Match match1 = Match.builder()
                .externalId(101L)
                .matchDay(matchDay)
                .homeTeam(teamA)
                .awayTeam(teamD)
                .homeScore(2)
                .awayScore(1)
                .status(Status.FINISHED)
                .build();

        Match match2 = Match.builder()
                .externalId(102L)
                .matchDay(matchDay)
                .homeTeam(teamB)
                .awayTeam(teamC)
                .homeScore(1)
                .awayScore(0)
                .status(Status.FINISHED)
                .build();

        when(competitionRepository.findById(1L))
                .thenReturn(Optional.of(competition));

        when(matchDayRepository.findByCompetitionAndLabel(
                competition,
                "J1"))
                .thenReturn(Optional.of(matchDay));

        when(matchRepository.findFinishedMatchesUntilMatchDay(
                1L,
                1,
                Status.FINISHED))
                .thenReturn(List.of(match1, match2));

        List<StandingResponseDto> standings =
                standingService.getStandings(1L, "J1");

        assertThat(standings).hasSize(4);

        StandingResponseDto teamAStanding = standings.stream()
                .filter(standing -> standing.team().id().equals(1L))
                .findFirst()
                .orElseThrow();

        StandingResponseDto teamBStanding = standings.stream()
                .filter(standing -> standing.team().id().equals(2L))
                .findFirst()
                .orElseThrow();

        // Both teams have the same number of points.
        assertThat(teamAStanding.points()).isEqualTo(3);
        assertThat(teamBStanding.points()).isEqualTo(3);

        // Both teams have the same goal difference.
        assertThat(teamAStanding.goalDifference()).isEqualTo(1);
        assertThat(teamBStanding.goalDifference()).isEqualTo(1);

        // Team A scored more goals and must therefore be ranked higher.
        assertThat(teamAStanding.goalsFor()).isEqualTo(2);
        assertThat(teamBStanding.goalsFor()).isEqualTo(1);

        assertThat(standings.indexOf(teamAStanding))
                .isLessThan(standings.indexOf(teamBStanding));
    }

    @Test
    void shouldThrowExceptionWhenCompetitionDoesNotExist() {

        /*
         * The requested competition does not exist.
         *
         * The service should throw a ResourceNotFoundException
         * and should not attempt to retrieve the matchday or matches.
         */

        when(competitionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                standingService.getStandings(1L, "J1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Competition not found with id: 1");

        // No matchday or match lookup should be performed
        // when the competition does not exist.
        verifyNoInteractions(matchDayRepository, matchRepository);
    }

    @Test
    void shouldThrowExceptionWhenMatchDayDoesNotExist() {

        /*
         * The competition exists, but the requested matchday
         * does not exist for this competition.
         *
         * The service should throw a ResourceNotFoundException
         * and should not attempt to retrieve any matches.
         */

        when(competitionRepository.findById(1L))
                .thenReturn(Optional.of(competition));

        when(matchDayRepository.findByCompetitionAndLabel(
                competition,
                "J2"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                standingService.getStandings(1L, "J2"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("MatchDay not found: J2");

        // No match lookup should be performed when the matchday
        // does not exist.
        verifyNoInteractions(matchRepository);
    }
}