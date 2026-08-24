package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.external.ExternalFixtureDto;
import fr.lh.resultsmanager.dtos.external.ExternalLeagueDto;
import fr.lh.resultsmanager.dtos.external.ExternalMatchDto;
import fr.lh.resultsmanager.dtos.external.ExternalMatchesResponse;
import fr.lh.resultsmanager.dtos.external.ExternalStatusDto;
import fr.lh.resultsmanager.dtos.external.ExternalTeamDto;
import fr.lh.resultsmanager.dtos.external.ExternalTeamsDto;
import fr.lh.resultsmanager.dtos.external.ExternalGoalsDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Competition;
import fr.lh.resultsmanager.model.League;
import fr.lh.resultsmanager.model.Match;
import fr.lh.resultsmanager.model.MatchDay;
import fr.lh.resultsmanager.model.Status;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.CompetitionRepository;
import fr.lh.resultsmanager.repository.LeagueRepository;
import fr.lh.resultsmanager.repository.MatchDayRepository;
import fr.lh.resultsmanager.repository.MatchRepository;
import fr.lh.resultsmanager.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchImportServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private MatchDayRepository matchDayRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchImportService matchImportService;

    private League league;
    private Competition competition;
    private MatchDay matchDay;
    private Team homeTeam;
    private Team awayTeam;

    @BeforeEach
    void setUp() {
        league = League.builder()
                .externalId(61L)
                .label("Ligue 1")
                .country("France")
                .level(1)
                .build();

        competition = Competition.builder()
                .season("2025")
                .league(league)
                .build();

        matchDay = MatchDay.builder()
                .competition(competition)
                .label("J3")
                .position(3)
                .build();

        homeTeam = Team.builder()
                .externalId(85L)
                .name("Paris Saint-Germain")
                .shortName("PSG")
                .city("Paris")
                .build();

        awayTeam = Team.builder()
                .externalId(81L)
                .name("Olympique de Marseille")
                .shortName("OM")
                .city("Marseille")
                .build();
    }

    @Test
    void shouldCreateNewMatch() {

        ExternalMatchDto dto = createMatch("FT");

        givenExistingDependencies(dto);

        when(matchRepository.findByExternalId(12345L))
                .thenReturn(Optional.empty());

        matchImportService.importMatches(
                new ExternalMatchesResponse(
                        null,
                        List.of(dto)
                )
        );

        ArgumentCaptor<Match> captor =
                ArgumentCaptor.forClass(Match.class);

        verify(matchRepository).save(captor.capture());

        Match savedMatch = captor.getValue();

        assertThat(savedMatch.getExternalId()).isEqualTo(12345L);
        assertThat(savedMatch.getMatchDay()).isEqualTo(matchDay);
        assertThat(savedMatch.getHomeTeam()).isEqualTo(homeTeam);
        assertThat(savedMatch.getAwayTeam()).isEqualTo(awayTeam);
        assertThat(savedMatch.getHomeScore()).isEqualTo(3);
        assertThat(savedMatch.getAwayScore()).isEqualTo(1);
        assertThat(savedMatch.getStatus()).isEqualTo(Status.FINISHED);
    }

    @Test
    void shouldUpdateExistingMatch() {

        ExternalMatchDto dto = createMatch("FT");

        givenExistingDependencies(dto);

        Match existingMatch = Match.builder()
                .externalId(12345L)
                .matchDay(matchDay)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeScore(1)
                .awayScore(0)
                .status(Status.LIVE)
                .build();

        when(matchRepository.findByExternalId(12345L))
                .thenReturn(Optional.of(existingMatch));

        matchImportService.importMatches(
                new ExternalMatchesResponse(
                        null,
                        List.of(dto)
                )
        );

        verify(matchRepository).save(existingMatch);

        assertThat(existingMatch.getHomeScore()).isEqualTo(3);
        assertThat(existingMatch.getAwayScore()).isEqualTo(1);
        assertThat(existingMatch.getStatus()).isEqualTo(Status.FINISHED);
        assertThat(existingMatch.getMatchDay()).isEqualTo(matchDay);
        assertThat(existingMatch.getHomeTeam()).isEqualTo(homeTeam);
        assertThat(existingMatch.getAwayTeam()).isEqualTo(awayTeam);
    }

    @ParameterizedTest
    @CsvSource({
            "NS, SCHEDULED",
            "1H, LIVE",
            "2H, LIVE",
            "HT, LIVE",
            "ET, LIVE",
            "P, LIVE",
            "FT, FINISHED",
            "AET, FINISHED",
            "PEN, FINISHED",
            "PST, CANCELLED",
            "CANC, CANCELLED"
    })
    void shouldConvertExternalStatus(
            String externalStatus,
            Status expectedStatus) {

        ExternalMatchDto dto = createMatch(externalStatus);

        givenExistingDependencies(dto);

        when(matchRepository.findByExternalId(12345L))
                .thenReturn(Optional.empty());

        matchImportService.importMatches(
                new ExternalMatchesResponse(
                        null,
                        List.of(dto)
                )
        );

        verify(matchRepository).save(
                org.mockito.ArgumentMatchers.argThat(
                        match -> match.getStatus() == expectedStatus
                )
        );
    }

    @Test
    void shouldThrowExceptionForUnknownExternalStatus() {

        ExternalMatchDto dto = createMatch("UNKNOWN");

        givenExistingDependencies(dto);

        when(matchRepository.findByExternalId(12345L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                matchImportService.importMatches(
                        new ExternalMatchesResponse(
                                null,
                                List.of(dto)
                        )
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported external status: UNKNOWN");

        verify(matchRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionForUnsupportedRound() {

        ExternalMatchDto dto = createMatchWithRound(
                "FT",
                "Final"
        );

        when(leagueRepository.findByExternalId(61L))
                .thenReturn(Optional.of(league));

        when(competitionRepository.findByLeagueAndSeason(
                league,
                "2025"
        )).thenReturn(Optional.of(competition));

        assertThatThrownBy(() ->
                matchImportService.importMatches(
                        new ExternalMatchesResponse(
                                null,
                                List.of(dto)
                        )
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported round format: Final");

        verify(matchDayRepository, never())
                .findByCompetitionAndLabel(any(), any());

        verify(matchRepository, never()).save(any());
    }

    private void givenExistingDependencies(ExternalMatchDto dto) {

        when(leagueRepository.findByExternalId(
                dto.league().id()
        )).thenReturn(Optional.of(league));

        when(competitionRepository.findByLeagueAndSeason(
                league,
                "2025"
        )).thenReturn(Optional.of(competition));

        when(matchDayRepository.findByCompetitionAndLabel(
                competition,
                "J3"
        )).thenReturn(Optional.of(matchDay));

        when(teamRepository.findByExternalId(
                dto.teams().home().id()
        )).thenReturn(Optional.of(homeTeam));

        when(teamRepository.findByExternalId(
                dto.teams().away().id()
        )).thenReturn(Optional.of(awayTeam));
    }

    private ExternalMatchDto createMatch(String status) {
        return createMatchWithRound(
                status,
                "Regular Season - 3"
        );
    }

    private ExternalMatchDto createMatchWithRound(
            String status,
            String round) {

        ExternalFixtureDto fixture = new ExternalFixtureDto(
                12345L,
                "2025-09-01T20:00:00+00:00",
                new ExternalStatusDto(
                        status,
                        status
                )
        );

        ExternalLeagueDto externalLeague =
                new ExternalLeagueDto(
                        61L,
                        "Ligue 1",
                        "France",
                        2025,
                        round
                );

        ExternalTeamsDto teams =
                new ExternalTeamsDto(
                        new ExternalTeamDto(
                                85L,
                                "Paris Saint-Germain",
                                "PSG",
                                null
                        ),
                        new ExternalTeamDto(
                                81L,
                                "Olympique de Marseille",
                                "OM",
                                null
                        )
                );

        ExternalGoalsDto goals =
                new ExternalGoalsDto(
                        3,
                        1
                );

        return new ExternalMatchDto(
                fixture,
                externalLeague,
                teams,
                goals
        );
    }

    @Test
    void shouldThrowExceptionWhenLeagueDoesNotExist() {

        ExternalMatchDto dto = createMatch("FT");

        when(leagueRepository.findByExternalId(61L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                matchImportService.importMatches(
                        new ExternalMatchesResponse(null, List.of(dto))
                )
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("League with externalId 61 not found");
    }
}