package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.external.ExternalTeamDto;
import fr.lh.resultsmanager.dtos.external.ExternalTeamResponse;
import fr.lh.resultsmanager.dtos.external.ExternalTeamsResponse;
import fr.lh.resultsmanager.dtos.external.ExternalVenueDto;
import fr.lh.resultsmanager.dtos.external.result.ImportResultDto;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamImportServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamImportService teamImportService;

    private ExternalTeamDto externalTeam;
    private ExternalVenueDto externalVenue;

    @BeforeEach
    void setUp() {
        externalTeam = new ExternalTeamDto(
                85L,
                "Paris Saint-Germain",
                "PSG",
                "logo.png"
        );

        externalVenue = new ExternalVenueDto(
                1L,
                "Parc des Princes",
                "24 Rue du Commandant Guilbaud",
                "Paris",
                47929,
                "grass",
                "stadium.png"
        );
    }

    @Test
    void shouldCreateNewTeam() {

        ExternalTeamResponse response =
                new ExternalTeamResponse(externalTeam, externalVenue);

        ExternalTeamsResponse teamsResponse = new ExternalTeamsResponse(
                "teams",
                null,
                List.of(),
                1,
                null,
                List.of(response)
        );

        when(teamRepository.findByExternalId(85L))
                .thenReturn(Optional.empty());

        ImportResultDto result =
                teamImportService.importTeams(teamsResponse);

        assertThat(result.total()).isEqualTo(1);
        assertThat(result.created()).isEqualTo(1);
        assertThat(result.ignored()).isZero();
        assertThat(result.failed()).isZero();

        verify(teamRepository).save(org.mockito.ArgumentMatchers.argThat(team ->
                team.getExternalId().equals(85L)
                        && team.getName().equals("Paris Saint-Germain")
                        && team.getShortName().equals("PSG")
                        && team.getCity().equals("Paris")
        ));
    }

    @Test
    void shouldIgnoreExistingTeam() {

        ExternalTeamResponse response =
                new ExternalTeamResponse(externalTeam, externalVenue);

        ExternalTeamsResponse teamsResponse = new ExternalTeamsResponse(
                "teams",
                null,
                List.of(),
                1,
                null,
                List.of(response)
        );

        Team existingTeam = Team.builder()
                .externalId(85L)
                .name("Paris Saint-Germain")
                .shortName("PSG")
                .city("Paris")
                .build();

        when(teamRepository.findByExternalId(85L))
                .thenReturn(Optional.of(existingTeam));

        ImportResultDto result =
                teamImportService.importTeams(teamsResponse);

        assertThat(result.total()).isEqualTo(1);
        assertThat(result.created()).isZero();
        assertThat(result.ignored()).isEqualTo(1);
        assertThat(result.failed()).isZero();

        verify(teamRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void shouldCreateTeamWithoutCityWhenVenueIsNull() {

        ExternalTeamResponse response =
                new ExternalTeamResponse(externalTeam, null);

        ExternalTeamsResponse teamsResponse = new ExternalTeamsResponse(
                "teams",
                null,
                List.of(),
                1,
                null,
                List.of(response)
        );

        when(teamRepository.findByExternalId(85L))
                .thenReturn(Optional.empty());

        ImportResultDto result =
                teamImportService.importTeams(teamsResponse);

        assertThat(result.total()).isEqualTo(1);
        assertThat(result.created()).isEqualTo(1);
        assertThat(result.ignored()).isZero();
        assertThat(result.failed()).isZero();

        verify(teamRepository).save(org.mockito.ArgumentMatchers.argThat(team ->
                team.getExternalId().equals(85L)
                        && team.getName().equals("Paris Saint-Germain")
                        && team.getShortName().equals("PSG")
                        && team.getCity() == null
        ));
    }

    @Test
    void shouldImportMultipleTeamsAndCountCreatedAndIgnoredTeams() {

        ExternalTeamDto newTeam1 = new ExternalTeamDto(
                85L,
                "Paris Saint-Germain",
                "PSG",
                "logo.png"
        );

        ExternalTeamDto existingTeam = new ExternalTeamDto(
                81L,
                "Olympique de Marseille",
                "OM",
                "logo.png"
        );

        ExternalTeamDto newTeam2 = new ExternalTeamDto(
                91L,
                "AS Monaco",
                "ASM",
                "logo.png"
        );

        ExternalTeamResponse response1 =
                new ExternalTeamResponse(newTeam1, externalVenue);

        ExternalTeamResponse response2 =
                new ExternalTeamResponse(existingTeam, externalVenue);

        ExternalTeamResponse response3 =
                new ExternalTeamResponse(newTeam2, externalVenue);

        ExternalTeamsResponse teamsResponse = new ExternalTeamsResponse(
                "teams",
                null,
                List.of(),
                3,
                null,
                List.of(response1, response2, response3)
        );

        when(teamRepository.findByExternalId(85L))
                .thenReturn(Optional.empty());

        when(teamRepository.findByExternalId(81L))
                .thenReturn(Optional.of(
                        Team.builder()
                                .externalId(81L)
                                .name("Olympique de Marseille")
                                .shortName("OM")
                                .city("Marseille")
                                .build()
                ));

        when(teamRepository.findByExternalId(91L))
                .thenReturn(Optional.empty());

        ImportResultDto result =
                teamImportService.importTeams(teamsResponse);

        assertThat(result.total()).isEqualTo(3);
        assertThat(result.created()).isEqualTo(2);
        assertThat(result.ignored()).isEqualTo(1);
        assertThat(result.failed()).isZero();

        verify(teamRepository).save(org.mockito.ArgumentMatchers.argThat(
                team -> team.getExternalId().equals(85L)
        ));

        verify(teamRepository).save(org.mockito.ArgumentMatchers.argThat(
                team -> team.getExternalId().equals(91L)
        ));

        verify(teamRepository, never()).save(org.mockito.ArgumentMatchers.argThat(
                team -> team.getExternalId().equals(81L)
        ));
    }
}