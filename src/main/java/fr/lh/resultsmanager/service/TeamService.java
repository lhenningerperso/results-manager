package fr.lh.resultsmanager.service;

import fr.lh.resultsmanager.dtos.request.TeamRequestDto;
import fr.lh.resultsmanager.exception.ResourceNotFoundException;
import fr.lh.resultsmanager.model.Team;
import fr.lh.resultsmanager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team createTeam(TeamRequestDto dto){
        return teamRepository.save(buildTeam(dto));
    }

    public List<Team> createTeams(List<TeamRequestDto> teamsDto){
        return teamRepository.saveAll(teamsDto.stream()
                .map(this::buildTeam)
                .toList());
    }

    /**
     * Get team by ID. The service will send the team data else will throw the exception.
     * @param teamId
     * @return CustomerData
     */
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("Team",teamId));
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    private Team buildTeam(TeamRequestDto dto){
        return Team.builder()
                .name(dto.getName())
                .shortName(dto.getShortName())
                .city(dto.getCity())
                .build();
    }

}
