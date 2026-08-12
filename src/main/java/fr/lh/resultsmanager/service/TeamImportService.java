package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.external.ExternalTeamDto;
import fr.lh.resultsmanager.dtos.external.ExternalTeamResponse;
import fr.lh.resultsmanager.dtos.external.ExternalTeamsResponse;
import fr.lh.resultsmanager.dtos.external.ExternalVenueDto;
import fr.lh.resultsmanager.dtos.external.result.ImportResultDto;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamImportService {

    private final TeamRepository teamRepository;

    public ImportResultDto importTeams(
            ExternalTeamsResponse response) {

        int created = 0;
        int ignored = 0;

        for (ExternalTeamResponse externalTeam : response.response()) {

            if (importTeam(externalTeam)) {
                created++;
            } else {
                ignored++;
            }
        }

        return new ImportResultDto(
                response.response().size(),
                created,
                ignored,
                0
        );
    }

    private boolean importTeam(
            ExternalTeamResponse externalTeam) {

        ExternalTeamDto dto = externalTeam.team();
        ExternalVenueDto venue = externalTeam.venue();

        if (teamRepository.findByExternalId(dto.id()).isPresent()) {
            return false;
        }

        Team team = Team.builder()
                .externalId(dto.id())
                .name(dto.name())
                .shortName(dto.code())
                .city(venue != null ? venue.city() : null)
                .build();

        teamRepository.save(team);

        return true;
    }
}
