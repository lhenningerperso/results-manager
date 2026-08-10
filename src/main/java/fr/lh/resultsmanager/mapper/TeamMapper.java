package fr.lh.resultsmanager.mapper;

import fr.lh.resultsmanager.dtos.response.TeamResponseDto;
import fr.lh.resultsmanager.model.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamMapper {

    public TeamResponseDto toDto(Team team) {
        return TeamResponseDto.builder()
                .id(team.getId())
                .teamLabel(team.getTeamLabel())
                .teamCity(team.getTeamCity())
                .build();
    }

    public List<TeamResponseDto> toDto(List<Team> teams){
        return teams.stream()
                .map(this::toDto)
                .toList();
    }

}
